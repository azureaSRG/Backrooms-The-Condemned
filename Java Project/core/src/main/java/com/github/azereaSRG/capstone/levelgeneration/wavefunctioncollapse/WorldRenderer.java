package com.github.azereaSRG.capstone.levelgeneration.wavefunctioncollapse;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class WorldRenderer {

    private World world;
    private OrthographicCamera camera;

    private TextureRegion[] tileSprites;

    public static final float TILE_SPACING = 4f;
    public static final float TILE_SIZE = 4f;
    public static final int SUBDIVISIONS = 8;

    public WorldRenderer(World world, OrthographicCamera camera, TextureRegion[] tileSprites) {
        this.world = world;
        this.camera = camera;
        this.tileSprites = tileSprites;
        System.out.println("Rendering World...");
    }

    public static float getTileScale() {
        return TILE_SPACING;
    }


    public void render(Batch batch) {
        float offsetX = world.getWidth() * TILE_SPACING / 2f;
        float offsetY = world.getHeight() * TILE_SPACING / 2f;

        float effectiveW = camera.viewportWidth * camera.zoom;
        float effectiveH = camera.viewportHeight * camera.zoom;

        // divide camera position by TILE_SPACING to get grid coordinates
        int startX = Math.max(0, (int)((camera.position.x - camera.viewportWidth/2 + offsetX) / TILE_SPACING));
        int startY = Math.max(0, (int)((camera.position.y - camera.viewportHeight/2 + offsetY) / TILE_SPACING));
        int endX = Math.min(world.getWidth(), startX + (int)(camera.viewportWidth / TILE_SPACING) + 2);
        int endY = Math.min(world.getHeight(), startY + (int)(camera.viewportHeight / TILE_SPACING) + 2);

        for (int x = startX; x < endX; x++) {
            for (int y = startY; y < endY; y++) {
                Cell cell = world.getCell(x, y);
                if (cell == null) continue;
                TileType tile = cell.collapsed;
                if (tile == null) continue;

                float subSize = (TILE_SIZE / SUBDIVISIONS);
                float baseX = x * TILE_SPACING - offsetX;
                float baseY = y * TILE_SPACING - offsetY;

                // subdivisions for textures
                for (int sx = 0; sx < SUBDIVISIONS; sx++) {
                    for (int sy = 0; sy < SUBDIVISIONS; sy++) {
                        float drawX = baseX + sx * subSize;
                        float drawY = baseY + sy * subSize;
                        float drawSize = (TILE_SIZE / SUBDIVISIONS) + 0.51f; // slight overlap

                        batch.draw(getRegion(tile), drawX, drawY, drawSize, drawSize);
                    }
                }
            }
        }
    }

    private TextureRegion getRegion(TileType tile) {
        switch(tile) {

            case VERTICAL: //add when other texture are added
                return tileSprites[0];

            case HORIZONTAL:
                return tileSprites[0];

            case L_NE:
                return tileSprites[0];

            case L_NW:
                return tileSprites[0];

            case L_SE:
                return tileSprites[0];

            case L_SW:
                return tileSprites[0];

            case T_N:
                return tileSprites[0];

            case T_E:
                return tileSprites[0];

            case T_S:
                return tileSprites[0];

            case T_W:
                return tileSprites[0];

            case PLUS:
                return tileSprites[0];

            case DEAD_N:
                return tileSprites[0];

            case DEAD_E:
                return tileSprites[0];

            case DEAD_S:
                return tileSprites[0];

            case DEAD_W:
                return tileSprites[0];

            case BUILDING:
                return tileSprites[1];

            case EMPTY:
                return tileSprites[2];

            case BACKUP:
                return tileSprites[2];

            default:
                return tileSprites[2];
        }
    }
}
