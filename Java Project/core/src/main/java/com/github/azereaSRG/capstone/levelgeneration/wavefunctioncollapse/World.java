package com.github.azereaSRG.capstone.levelgeneration.wavefunctioncollapse;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.lang.reflect.Array;
import java.util.*;

/**
 * creates the initial spawn area for the player using a wave function collapse algorithm
*/
public class World {
    //inital world spawn
    private Cell[][] grid;

    //dimensions of inital world spawn
    private int width = 250;
    private int height = 250;

    //grid backbone to prevent total randomness
    private final float GRID_DISCONNECT_CHANCE = 0.25f;
    private final int ROAD_SPACING = 7; //old usage => 7
    private RoadNode[][] roadGraph;

    //possible wave states
    private Random rand = new Random();
    private Queue<Vector2> propagateQueue = new LinkedList<>();

    //disconnects random roads in the backbone grid to add a degree of randomness
    private void removeRandomRoads(float chance) {
        for(RoadNode[] row : roadGraph) {
            for(RoadNode node : row) {
                List<RoadNode> copy = new ArrayList<>(node.connections);

                for(RoadNode neighbor : copy) {
                    if(rand.nextFloat() < chance) {
                        // don't disconnect everything
                        if(node.connections.size() > 1 &&
                            neighbor.connections.size() > 1) {
                            node.disconnect(neighbor);
                        }
                    }
                }
            }
        }
    }

    //creates backbone with random disconnects
    private boolean[][] createBackbone() {
        boolean[][] roadMask = new boolean[width][height];

        createRoadGraph();
        removeRandomRoads(GRID_DISCONNECT_CHANCE);

        for(RoadNode[] row : roadGraph) {
            for(RoadNode node : row) {
                for(RoadNode neighbor : node.connections) {
                    drawLine(roadMask, node.pos, neighbor.pos);
                }
            }
        }

        return roadMask;
    }

    //creates inital grid
    private void createRoadGraph() {
        int graphWidth = width / ROAD_SPACING;
        int graphHeight = height / ROAD_SPACING;

        roadGraph = new RoadNode[graphWidth][graphHeight];

        // create nodes
        for(int gx = 0; gx < graphWidth; gx++) {
            for(int gy = 0; gy < graphHeight; gy++) {
                roadGraph[gx][gy] =
                    new RoadNode(new Vector2(
                        gx * ROAD_SPACING,
                        gy * ROAD_SPACING
                    ));
            }
        }

        // connect neighbors
        for(int gx = 0; gx < graphWidth; gx++) {
            for(int gy = 0; gy < graphHeight; gy++) {
                RoadNode current = roadGraph[gx][gy];
                if(gx + 1 < graphWidth) current.connect(roadGraph[gx + 1][gy]);
                if(gy + 1 < graphHeight) current.connect(roadGraph[gx][gy + 1]);
            }
        }
    }

    //draws lines between road nodes
    private void drawLine(boolean[][] roadMask, Vector2 start, Vector2 end) {
        int x1 = (int) start.x;
        int y1 = (int) start.y;
        int x2 = (int) end.x;
        int y2 = (int) end.y;

        // horizontal segment
        int minX = Math.min(x1, x2);
        int maxX = Math.max(x1, x2);

        for (int x = minX; x <= maxX; x++) {
            roadMask[x][y1] = true;
        }

        // vertical segment
        int minY = Math.min(y1, y2);
        int maxY = Math.max(y1, y2);

        for (int y = minY; y <= maxY; y++) {
            roadMask[x2][y] = true;
        }
    }

    //generates world
    public void generate() {
        System.out.println("Generating World...");
        grid = new Cell[width][height];
        boolean[][] roadMask;
        roadMask = createBackbone();

        // initalizes world
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                grid[x][y] = new Cell();
                if (roadMask[x][y]) { //all possible (valid & invalid) states
                    grid[x][y].possibilities.addAll(Arrays.asList(
                        TileType.VERTICAL,
                        TileType.HORIZONTAL,
                        TileType.L_NE,
                        TileType.L_NW,
                        TileType.L_SE,
                        TileType.L_SW,
                        TileType.T_N,
                        TileType.T_E,
                        TileType.T_S,
                        TileType.T_W,
                        TileType.PLUS,
                        TileType.DEAD_N,
                        TileType.DEAD_W,
                        TileType.DEAD_E,
                        TileType.DEAD_S
                    ));
                }
                else { //all non-road states
                    grid[x][y].possibilities.addAll(Arrays.asList(
                        TileType.EMPTY,
                        TileType.BUILDING
                    ));
                }

            }
        }

        // start from center
        propagateQueue.add(new Vector2(width/2, height/2));
        runWFC(); //runs wave function collapse
    }

    /**
     * @return - void
     * @desc - starts from one position and collapses neighboring nodes until the grid is fulfilled
    */
    private void runWFC() {
        while(true) { //repeats till all positions are filled
            Vector2 pos = findLowestEntropyCell(); //finds position with lowest possible states
            if(pos == null)
                break;

            Cell cell = grid[(int)pos.x][(int)pos.y];
            cell.collapse(rand);
            propagate(pos);
        }
    }

    // changes possible neighbor cells based on own state
    private void propagate(Vector2 pos) {
        int x = (int) pos.x;
        int y = (int) pos.y;

        TileType tile = grid[x][y].collapsed;
        if(tile == null) { //when current cell does not have a state
            throw new RuntimeException(
                "Propagating from uncollapsed cell at "
                    + x + "," + y
            );
        }

        int[][] dirs = {
            {0, 1},   // top
            {0, -1},  // bottom
            {1, 0},   // right
            {-1, 0}   // left
        };

        for (int i = 0; i < 4; i++) { //repeats for each offset
            int nx = x + dirs[i][0];
            int ny = y + dirs[i][1];
            if (!inBounds(nx,ny)) continue;

            Cell neighbor = grid[nx][ny];

            if (neighbor.isCollapsed()) continue; //breaks if neighbor already has set state

            Set<TileType> newSet = new HashSet<>();

            for (TileType candidate : neighbor.possibilities) {
                if (isCompatible(candidate, tile, i)) {
                    newSet.add(candidate);
                }
            }

            if (newSet.isEmpty()) { //sometimes there the algorithm finds no possible state
                try { //throws exception which prints in console
                    noCompatibleNodes(nx, ny);
                }
                catch (Exception e) {
                    System.out.println(" Defaulted to Backup Cell... Issue has been Resolved.");
                    newSet.add(TileType.BACKUP); //sets cell to empty / backup
                }
            }

            if (newSet.size() != neighbor.possibilities.size()) {
                neighbor.possibilities = newSet;
            }
        }
    }



    //throws a NoCompatibleNodesException
    private void noCompatibleNodes(int x, int y) throws RuntimeException{
        throw new NoCompatibleNodes("Attempted to collapse wave on " + x + ", " + y +
            " but found no compatible nodes.");
    }

    //Checks if vector is within grid bounds
    private boolean inBounds(int x, int y) {
        return (x > -1 && x < width && y > -1 && y < height);
//        return (v2.x > -1 && v2.x < width && v2.y > -1 && v2.y < height);
    }

    /**
     * filters down the potential nodes for the collapse
     */
    private boolean isCompatible(TileType a, TileType b, int direction) {
        switch (direction) {
            case 0: // neighbor is above
                return a.bottom == b.top;
            case 1: // neighbor is below
                return a.top == b.bottom;
            case 2: // neighbor is right
                return a.left == b.right;
            case 3: // neighbor is left
                return a.right == b.left;
            default:
                return false;
        }
    }

    //finds cell with least possible amount of states
    private Vector2 findLowestEntropyCell() {
        Vector2 best = null;
        int bestEntropy = Integer.MAX_VALUE;

        for(int x=0;x<width;x++) {
            for(int y=0;y<height;y++) {
                Cell cell = grid[x][y];
                if(cell.isCollapsed())
                    continue;

                int entropy = cell.entropy();
                if(entropy == 0) {

                    throw new RuntimeException(
                        "Impossible cell at "
                            + x + "," + y
                    );
                }
                if(entropy < bestEntropy) {
                    bestEntropy = entropy;
                    best = new Vector2(x,y);
                }
            }
        }

        return best;
    }

    // In World.java
    public ArrayList<Rectangle> getWallBounds(float tileSpacing, float tileSize) {
        ArrayList<Rectangle> walls = new ArrayList<>();
        float offsetX = width * tileSpacing / 2f;
        float offsetY = height * tileSpacing / 2f;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Cell cell = grid[x][y];
                if (cell == null || cell.collapsed == null) continue;
                if (cell.collapsed == TileType.EMPTY ||
//                    cell.collapsed == TileType.BUILDING ||
                    cell.collapsed == TileType.BACKUP) {
                    walls.add(new Rectangle(
                        x * tileSpacing - (offsetX),
                        y * tileSpacing - (offsetY),
                        tileSpacing,
                        tileSpacing
                    ));
                }
            }
        }
        return walls;
    }

    public Vector2 findSafeSpawn(float tileSpacing) {
        float offsetX = width * tileSpacing / 2f;
        float offsetY = height * tileSpacing / 2f;

        // Start from center and spiral outward looking for a road tile
        int centerX = width / 2;
        int centerY = height / 2;

        for (int radius = 0; radius < Math.max(width, height); radius++) {
            for (int dx = -radius; dx <= radius; dx++) {
                for (int dy = -radius; dy <= radius; dy++) {
                    // Only check the edge of the current radius
                    if (Math.abs(dx) != radius && Math.abs(dy) != radius) continue;

                    int x = centerX + dx;
                    int y = centerY + dy;

                    if (!inBounds(x, y)) continue;

                    Cell cell = grid[x][y];
                    if (cell == null || cell.collapsed == null) continue;

                    // Spawn on road tiles only
                    if (isRoadTile(cell.collapsed)) {
                        return new Vector2(
                            x * tileSpacing - offsetX,
                            y * tileSpacing - offsetY
                        );
                    }
                }
            }
        }

        // Fallback to center if no road found (shouldn't happen)
        return new Vector2(0, 0);
    }

    private boolean isRoadTile(TileType tile) {
        switch (tile) {
            case VERTICAL:
            case HORIZONTAL:
            case L_NE:
            case L_NW:
            case L_SE:
            case L_SW:
            case T_N:
            case T_E:
            case T_S:
            case T_W:
            case PLUS:
            case DEAD_N:
            case DEAD_E:
            case DEAD_S:
            case DEAD_W:
                return true;
            default:
                return false;
        }
    }

    /**
     * prints out the world according to names
     */
    public void print() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Cell c = grid[x][y];
                if (c.collapsed == null) {
                    System.out.print("?");
                } else {
                    System.out.print(symbol(c.collapsed));
                }
            }
            System.out.println();
        }
    }

    //returns symbol of cell for console reasons
    private String symbol(TileType type) {
        return type.symbol;
    }

    public Cell getCell(int x, int y) {
        return grid[x][y];
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
}

/**
 * Class for Backbone Connection Nodes
 */
class RoadNode {
    Vector2 pos;

    RoadNode north;
    RoadNode south;
    RoadNode east;
    RoadNode west;

    List<RoadNode> connections = new ArrayList<>();

    public RoadNode(Vector2 pos) {
        this.pos = pos;
    }

    //connects nodes
    public void connect(RoadNode other) {
        if(other == null) return;
        if(!connections.contains(other)) connections.add(other);
        if(!other.connections.contains(this)) other.connections.add(this);
    }

    //disconnects nodes
    public void disconnect(RoadNode other) {
        connections.remove(other);
        other.connections.remove(this);
    }
}

