package com.github.azereaSRG.capstone.gamemenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.azereaSRG.capstone.entityutil.Faceling.*;
import com.github.azereaSRG.capstone.Main;
import com.github.azereaSRG.capstone.levelgeneration.wavefunctioncollapse.TileType;
import com.github.azereaSRG.capstone.levelgeneration.wavefunctioncollapse.World;
import com.github.azereaSRG.capstone.levelgeneration.wavefunctioncollapse.WorldRenderer;
import com.github.azereaSRG.capstone.playerutil.Player;

import static com.github.azereaSRG.capstone.levelgeneration.wavefunctioncollapse.WorldRenderer.TILE_SPACING;

public class GameScreen extends ScreenAdapter {
    private static final float WORLD_WIDTH = 32f * WorldRenderer.getTileScale();
    private static final float WORLD_HEIGHT = 18f * WorldRenderer.getTileScale();
    private static final int DAY_LENGTH = 600; //in seconds
    private static final int NIGHT_LENGTH = 600;
    private static final int TILE_SIZE = 1;
    private final Main game;
    private static int dayCounter = 0;
    private static boolean isNight = false;
    private static float timer;

    private World world;
    private WorldRenderer worldRenderer;
    private ShapeRenderer shapeRenderer;

    private Batch batch;
    private Texture bgdTexture = new Texture(Gdx.files.internal("bgd.png"));
    private Texture playerFront, playerBack, playerRight, playerLeft;
    private OrthographicCamera camera;
    private Viewport gameViewport;
    private final Vector2 inputMovement = new Vector2();
    private Texture tileSheet;
    private TextureRegion[] sprites;

    private final Player player;
    private final FacelingInterface enemy;

    private final Vector2 worldCenter;

    public GameScreen(Main game) {
        this.game = game;
        this.batch = game.getBatch();

        shapeRenderer = new ShapeRenderer();
        timer = 0;

        camera = new OrthographicCamera();
        camera.zoom = 0.15f;

        gameViewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        tileSheet = new Texture(Gdx.files.internal("sheet.png"));

        //manually sets and checks texture region positions
//        System.out.println("Sheet: " + tileSheet.getWidth() + "x" + tileSheet.getHeight());
//        System.out.println("Sheet loaded: " + tileSheet.getWidth() + "x" + tileSheet.getHeight());

        TextureRegion roadTile = new TextureRegion();
        roadTile.setTexture(tileSheet);
        roadTile.setRegion(0, 0, 383, 384);

        TextureRegion buildingTile = new TextureRegion();
        buildingTile.setTexture(tileSheet);
        buildingTile.setRegion(384, 0, 383, 384);

        TextureRegion emptyTile = new TextureRegion();
        emptyTile.setTexture(tileSheet);
        emptyTile.setRegion(767, 0, 383, 384);

        sprites = new TextureRegion[] {
            roadTile,     // VERTICAL
            buildingTile, // BUILDING
            emptyTile     // EMPTY
        };

        // texture debugging
//        System.out.println("Road region: " + roadTile.getRegionWidth() + "x" + roadTile.getRegionHeight());
//        System.out.println("Building region: " + buildingTile.getRegionWidth() + "x" + buildingTile.getRegionHeight());
//        System.out.println("Empty region: " + emptyTile.getRegionWidth() + "x" + emptyTile.getRegionHeight());
//        System.out.println("tileSprites[0] (road): " + sprites[0].getRegionX());
//        System.out.println("tileSprites[1] (building): " + sprites[1].getRegionX());
//        System.out.println("tileSprites[2] (empty): " + sprites[2].getRegionX());
//        System.out.println("road x: " + roadTile.getRegionX());
//        System.out.println("building x: " + buildingTile.getRegionX());
//        System.out.println("empty x: " + emptyTile.getRegionX());

        world = new World();
        world.generate();

        worldRenderer = new WorldRenderer(world, camera, sprites);

        world.print();

        // change spawns to match world coordinates
        worldCenter = new Vector2(world.getWidth()/2f, world.getHeight()/2f);
        Vector2 safeSpawn = world.findSafeSpawn(TILE_SPACING);

        playerFront = new Texture(Gdx.files.internal("larry/larry-front-sprite.png"));
        playerBack  = new Texture(Gdx.files.internal("larry/larry-back-sprite.png"));
        playerLeft  = new Texture(Gdx.files.internal("larry/larry-left-sprite.png"));
        playerRight = new Texture(Gdx.files.internal("larry/larry-right-sprite.png"));

        player = new Player(
            safeSpawn.x, safeSpawn.y, gameViewport, playerFront, playerBack, playerLeft, playerRight,
            world.getWidth() * TILE_SPACING, world.getHeight() * TILE_SPACING);
        player.setWallBounds(world.getWallBounds(TILE_SPACING, TILE_SPACING));

        enemy = new Stranger(worldCenter.x,worldCenter.y,new Texture(Gdx.files.internal(
            "itemDesigns/Finalized Designs/lilguy.png"))
            ,player);
        bgdTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
    }

    public static float getWorldHeight() {
        return WORLD_HEIGHT;
    }

    public static float getWorldWidth() {
        return WORLD_WIDTH;
    }

    @Override
    public void resize(int width, int height) {
        gameViewport.update(width, height, true);
    }

    @Override
    public void show() {
        resetGame();
    }

    private void resetGame() {
        Vector2 safeSpawn = world.findSafeSpawn(TILE_SPACING);
        player.reset(safeSpawn.x, safeSpawn.y);
    }

    private void getPlayerInput() {
         inputMovement.setZero();
         if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            player.accessInventory();
            return;
         }
         if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
             player.useItem(0,0);

             return;
         }
         playerMovementInputs();
    }

    private void playerMovementInputs() {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            inputMovement.y += 1;
            player.setDirection(3);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            inputMovement.y -= 1;
            player.setDirection(1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            inputMovement.x -= 1;
            player.setDirection(2);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            inputMovement.x += 1;
            player.setDirection(0);
        }

        inputMovement.nor(); //Normalized Vector
//        debugMovement();
        player.updateDirection(inputMovement);
    }

    private void updatePlayer(float delta) {
        player.update(delta);
    }

    private void debugMovement() {
        System.out.println(player.getX() + ", " + player.getY());
    }

    @Override
    public void render(float deltaTime) {
        getPlayerInput();
        updatePlayer(deltaTime);
        checkTimer(deltaTime);

        float visibleW = camera.viewportWidth * camera.zoom;
        float visibleH = camera.viewportHeight * camera.zoom;

        int startX = Math.max(0, (int)((camera.position.x - visibleW/2) / TILE_SIZE));

        gameViewport.apply(true);
        camera.position.set(player.getX(), player.getY(), 0);
        camera.update();
        ScreenUtils.clear(Color.WHITE);
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        float uW = gameViewport.getWorldWidth() / WORLD_WIDTH;
        float vH = gameViewport.getWorldHeight() / WORLD_HEIGHT;
//        batch.draw(bgdTexture, worldCenter.x, worldCenter.y, gameViewport.getWorldWidth()
//            , gameViewport.getWorldHeight(), 0, 0, uW, vH);
        worldRenderer.render(batch);

        player.draw(batch);

        enemy.draw(batch);
        enemy.update(deltaTime);

        batch.end();

        // wall collision debug

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        for (Rectangle wall : world.getWallBounds(TILE_SPACING, TILE_SIZE)) {
            shapeRenderer.rect(wall.x, wall.y, wall.width, wall.height);
        }
        shapeRenderer.end();


//        float offsetX = world.getWidth() * WorldRenderer.TILE_SPACING / 2f;
//        float offsetY = world.getHeight() * WorldRenderer.TILE_SPACING / 2f;
//
//        System.out.println("Tile(0,0) draw pos: " + (0 - offsetX) + ", " + (0 - offsetY));
//
//        float wallX = (0 * WorldRenderer.TILE_SPACING) - offsetX;
//        float wallY = (0 * WorldRenderer.TILE_SPACING) - offsetY;
//        System.out.println("Wall(0,0) collision pos: " + wallX + ", " + wallY);
    }

    @Override
    public void dispose() {
        bgdTexture.dispose();
        playerFront.dispose();
        playerBack.dispose();
        playerLeft.dispose();
        playerRight.dispose();
        tileSheet.dispose();
        shapeRenderer.dispose();
    }

    public static void checkTimer(float timeIncrease) {
        timer += timeIncrease;
        if (!isNight && timer >= DAY_LENGTH) {isNight = true;}
        if (timer > DAY_LENGTH + NIGHT_LENGTH) {
            timer = 0;
        }
    }
}
