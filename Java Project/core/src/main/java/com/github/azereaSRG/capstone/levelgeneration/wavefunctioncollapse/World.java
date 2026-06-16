package com.github.azereaSRG.capstone.levelgeneration.wavefunctioncollapse;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * I have Wave Function
 */
class NodeDefinition {
    ConnectionType top;
    ConnectionType bottom;
    ConnectionType left;
    ConnectionType right;
}

public class World {
    private int width = 10;
    private int height = 10;

    private Node[][] world;
    private List<Class<? extends Node>> nodes = new ArrayList<>(List.of(
        StraightNode.Horizontal.class,
        StraightNode.Vertical.class,
        CurvedNode.LPiece.class,
        CurvedNode.RPiece.class,
        CurvedNode.MLPiece.class,
        CurvedNode.MRPiece.class,
        EmptyNode.Empty.class
    ));
    private List<Vector2> toCollapse = new ArrayList<>();
    private Random rand = new Random();

    Vector2[] offsets = new Vector2[] {
        new Vector2(0,1),   //Top
        new Vector2(0, -1), //Bottom
        new Vector2(1, 0),  //Right
        new Vector2(-1,0)   //Left
    };

    public void generate() {
        world = new Node[width][height];
        collapse();
    }

    private void collapse() {
        toCollapse.clear();
        toCollapse.add(new Vector2((int) (width/2),(int) (height/2)));

        //If node is in a superposition
        while (!toCollapse.isEmpty()) {
            int x = (int) toCollapse.get(0).x;
            int y = (int) toCollapse.get(0).y;

            //All possible nodes (including invalid ones)
            ArrayList<Class<? extends Node>> potentialNodes =
                new ArrayList<>(nodes);

            for (int i = 0; i < offsets.length; i++) {
                Vector2 neighbor = new Vector2( x + offsets[i].x,y + offsets[i].y);

                //Checks boundries
                if (isInsideGrid(neighbor)) {
                    Node neighborNode = world[(int) neighbor.x][(int) neighbor.y];

                    if (neighborNode != null) {
                        switch (i) {
                            case 0: // top neighbor
                                filterCandidates(potentialNodes, neighborNode, 0);
                                break;

                            case 1: // bottom neighbor
                                filterCandidates(potentialNodes, neighborNode, 1);
                                break;

                            case 2: // right neighbor
                                filterCandidates(potentialNodes, neighborNode, 2);
                                break;

                            case 3: // left neighbor
                                filterCandidates(potentialNodes, neighborNode, 3);
                                break;
                        }
                    }
                    else {
                        if (!toCollapse.contains(neighbor)) {
                            toCollapse.add(neighbor);
                        }
                    }
                }

                if (potentialNodes.size() < 1) {
                    //Create more tiles
                    try {
                        world[x][y] = nodes.get(0).newInstance();
                        noCompatibleNodes(x, y);
                    } catch (RuntimeException e) {
                        System.out.println();
                    } catch (InstantiationException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                    //Loop
                }
                else {
                    Class<? extends Node> chosen =
                        potentialNodes.get(rand.nextInt(potentialNodes.size()));
                    try { //Needed for declared constructor
                        world[x][y] =
                            chosen.getDeclaredConstructor().newInstance();
                    }
                    catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            } toCollapse.remove(0);
        }
    }

    private void noCompatibleNodes(int x, int y) throws RuntimeException{
        throw new NoCompatibleNodes("Attempted to collapse wave on " + x + ", " + y +
            " but found no compatible nodes.");
    }

    //Checks if vector is within grid bounds
    private boolean isInsideGrid(Vector2 v2) {
        return (v2.x > -1 && v2.x < width && v2.y > -1 && v2.y < height);
    }

    private void filterCandidates(
        ArrayList<Class<? extends Node>> candidates,
        Node neighbor,
        int direction)
    {
        for (int i = candidates.size() - 1; i >= 0; i--) {

            try {
                Node candidate =
                    candidates.get(i)
                        .getDeclaredConstructor()
                        .newInstance();
                boolean valid;

                switch (direction) {

                    case 0:
                        valid = candidate.top == neighbor.bottom;
                        break;

                    case 1:
                        valid = candidate.bottom == neighbor.top;
                        break;

                    case 2:
                        valid = candidate.right == neighbor.left;
                        break;

                    case 3:
                        valid = candidate.left == neighbor.right;
                        break;

                    default:
                        valid = false;
                }

                if (!valid) {
                    candidates.remove(i);
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void print() {
        for (Node[] row : world) {
            for (Node node : row) {
                System.out.print(node.name);
            }
            System.out.println();
        }
    }
}

