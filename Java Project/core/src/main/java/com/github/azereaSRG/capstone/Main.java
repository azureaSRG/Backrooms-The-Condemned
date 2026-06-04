package com.github.azereaSRG.capstone;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.azereaSRG.capstone.EntityScripts.Faceling;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    private SpriteBatch batch;
    private Texture image;

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("libgdx16.png");
        setScreen(new FirstScreen());
        executeMain();
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.draw(image, 140, 210);
        batch.end();
    }

    @Override
    public void dispose() {
        System.out.println("Stopped Execution of Main");
        batch.dispose();
        image.dispose();
    }

    public static void executeMain() {
        System.out.println("Executing Main...");

        Camera camera = new PerspectiveCamera();
        Viewport viewport = new FitViewport(800,480, camera);

        Player player = new Player(0,0, viewport, new Texture("libgdx16.png"));

        Faceling.Stranger entity = new Faceling.Stranger(0,0,new Texture("libgdx16.png"), player);
        entity.runMain();
    }
}
