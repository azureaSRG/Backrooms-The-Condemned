package com.github.azereaSRG.capstone.Menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.azereaSRG.capstone.Main;
import com.github.azereaSRG.capstone.PlayerScripts.Player;

public class GameScreen extends ScreenAdapter {
    private static final float WORLD_WIDTH = 16f;
    private static final float WORLD_HEIGHT = 9f;
    private final Main game;

    private Batch batch;
    private Texture bgdTexture = new Texture(Gdx.files.internal("bgd.png"));
    private Texture playerTexture = new Texture(Gdx.files.internal("larry/larry-front-sprite.png"));
    private Viewport gameViewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT);
    private final Vector2 inputMovement = new Vector2();

    private final Player player = new Player(WORLD_WIDTH/2f, WORLD_HEIGHT/2f,
        gameViewport, playerTexture);

    public GameScreen(Main game) {
        this.game = game;
        this.batch = game.getBatch();

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
        player.reset(WORLD_WIDTH/2f, WORLD_HEIGHT/2f);
    }

    private void getPlayerInput() {
         inputMovement.setZero();
         if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            player.accessInventory();
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
        player.updateDirection(inputMovement);
    }

    private void updatePlayer(float delta) {
        player.update(delta);
    }

    @Override
    public void render(float deltaTime) {
        getPlayerInput();
        updatePlayer(deltaTime);

        ScreenUtils.clear(Color.BLACK);

        gameViewport.apply();
        batch.setProjectionMatrix(gameViewport.getCamera().combined);
        batch.begin();

        float uW = gameViewport.getWorldWidth() / WORLD_WIDTH;
        float vH = gameViewport.getWorldHeight() / WORLD_HEIGHT;

        batch.draw(bgdTexture, 0, 0, gameViewport.getWorldWidth()
            , gameViewport.getWorldHeight(), 0, 0, uW, vH);
        player.draw(batch);

        batch.end();
    }

    @Override
    public void dispose() {
        bgdTexture.dispose();
        playerTexture.dispose();
    }
}
