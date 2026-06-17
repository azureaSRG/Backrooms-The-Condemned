package com.github.azereaSRG.capstone.gamemenus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.azereaSRG.capstone.Main;

import com.badlogic.gdx.graphics.Color;

public class MainMenu extends ScreenAdapter {
    private final Main game;
    private final Batch batch;
    private final BitmapFont font;
    private final Viewport viewport = new ScreenViewport();
    private Texture titleTexture;
    private Sprite title;

    public MainMenu(Main game) {
        this.game = game;
        this.batch = game.getBatch();
        this.font = game.getFont();
        titleTexture = new Texture(Gdx.files.internal("menuUI/main_menu_title.png"));
        title = new Sprite(titleTexture, 0,0,1080,1080);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.setScreen(new GameScreen(game));
            dispose();
            return;
        }

        ScreenUtils.clear(Color.BLACK);

        viewport.apply();

        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        float screenCenterX = Gdx.graphics.getWidth() / 2f;
        float screenCenterY = (Gdx.graphics.getHeight() / 2f);

        batch.draw(titleTexture, screenCenterX - titleTexture.getWidth() / 4f,
            screenCenterY - titleTexture.getHeight() / 4f,
            titleTexture.getWidth()/2f, titleTexture.getHeight()/2f);
        batch.end();
    }
}
