package com.github.azereaSRG.capstone.Menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.azereaSRG.capstone.Main;

import com.badlogic.gdx.graphics.Color;

public class MainMenu extends ScreenAdapter {
    private final Main game;
    private final Batch batch;
    private final BitmapFont font;
    private final Viewport viewport = new ScreenViewport();

    public MainMenu(Main game) {
        this.game = game;
        this.batch = game.getBatch();
        this.font = game.getFont();
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
        batch.end();
    }
}
