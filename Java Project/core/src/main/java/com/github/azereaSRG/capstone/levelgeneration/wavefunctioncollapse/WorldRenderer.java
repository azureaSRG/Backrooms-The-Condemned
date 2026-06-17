package com.github.azereaSRG.capstone.levelgeneration.wavefunctioncollapse;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class WorldRenderer {

    private World world;
    private OrthographicCamera camera;

    private TextureRegion[] tileSprites;

    private static final int TILE_SIZE = 1;

    public WorldRenderer(
        World world,
        OrthographicCamera camera,
        TextureRegion[] tileSprites)
    {
        this.world = world;
        this.camera = camera;
        this.tileSprites = tileSprites;
    }

    public void render(Batch batch) {
        int startX = Math.max(0, (int)(camera.position.x - camera.viewportWidth/2));
        int startY = Math.max(0, (int)(camera.position.y - camera.viewportHeight/2));
        int endX = Math.min(world.getWidth(), startX + (int)camera.viewportWidth + 2);
        int endY = Math.min(world.getHeight(), startY + (int)camera.viewportHeight + 2);

        for (int x = startX; x < endX; x++) {
            for (int y = startY; y < endY; y++) {
                Cell cell = world.getCell(x, y);
                if (cell == null) continue;
                TileType tile = cell.collapsed;
                if (tile == null) continue;
                float offsetX = world.getWidth() / 2f;
                float offsetY = world.getHeight() / 2f;

                batch.draw(getRegion(tile), x - offsetX, y - offsetY, 1, 1);
            }
        }
    }

    private TextureRegion getRegion(TileType tile) {
        switch(tile) {

            case VERTICAL:
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

            default:
                return tileSprites[2];
        }
    }
}
