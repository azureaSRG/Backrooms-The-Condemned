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

public class GameScreen extends ScreenAdapter {
    private static final float WORLD_WIDTH = 32f;
    private static final float WORLD_HEIGHT = 18f;
    private static final int DAY_LENGTH = 600; //in seconds
    private static final int NIGHT_LENGTH = 600;
    private static final int TILE_SIZE = 1;
    private final Main game;
    private static int dayCounter = 0;
    private static boolean isNight = false;
    private static float timer;

    private World world;
    private WorldRenderer worldRenderer;

    private Batch batch;
    private Texture bgdTexture = new Texture(Gdx.files.internal("bgd.png"));
    private Texture playerTexture = new Texture(Gdx.files.internal("larry/larry-front-sprite(Single).png"));
    private OrthographicCamera camera;
    private Viewport gameViewport;
    private final Vector2 inputMovement = new Vector2();
    private Texture tileSheet;
    private TextureRegion[] sprites;

    private final Player player;
    private final FacelingInterface enemy;


    public GameScreen(Main game) {
        this.game = game;
        this.batch = game.getBatch();
        timer = 0;

        camera = new OrthographicCamera();
        gameViewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        tileSheet = new Texture(Gdx.files.internal("sheet.png"));

        TextureRegion[][] split = TextureRegion.split(tileSheet, TILE_SIZE, TILE_SIZE);

        sprites = new TextureRegion[] {
            split[0][0], // vertical
            split[0][0], // horizontal
            split[0][0], // l_ne
            split[0][0], // l_nw
            split[0][0], // l_se
            split[0][0], // l_sw
            split[0][0], // t_n
            split[0][0], // t_e
            split[0][0], // t_s
            split[0][0], // t_w
            split[0][0], // plus
            split[0][0], // dead_n
            split[0][0], // dead_e
            split[0][0], // dead_s
            split[0][0], // dead_w
            split[0][1], // building
            split[0][2]  // empty
        };

        world = new World();
        world.generate();

        worldRenderer = new WorldRenderer(world, camera, sprites);

        world.print();
        // Change player spawn to match world coordinates
        player = new Player(world.getWidth() / 2f, world.getHeight() / 2f,
            gameViewport, playerTexture);
        enemy = new Stranger(0,0,new Texture(Gdx.files.internal(
            "itemDesigns/Finalized Designs/lilguy.png"))
            ,player);
        bgdTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
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
        player.reset(world.getWidth() / 2f, world.getHeight() / 2f);
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
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            inputMovement.y -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            inputMovement.x -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            inputMovement.x += 1;
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
        batch.draw(bgdTexture, 0, 0, gameViewport.getWorldWidth()
            , gameViewport.getWorldHeight(), 0, 0, uW, vH);
        worldRenderer.render(batch);


        player.draw(batch);


        enemy.draw(batch);
        enemy.update(deltaTime);


        batch.end();
    }

    @Override
    public void dispose() {
        bgdTexture.dispose();
        playerTexture.dispose();
        tileSheet.dispose();
    }

    public static void checkTimer(float timeIncrease) {
        timer += timeIncrease;
        if (!isNight && timer >= DAY_LENGTH) {isNight = true;}
        if (timer > DAY_LENGTH + NIGHT_LENGTH) {
            timer = 0;
        }
    }
}
