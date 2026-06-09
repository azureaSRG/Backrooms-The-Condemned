package com.github.azereaSRG.capstone;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.azereaSRG.capstone.EntityScripts.Faceling;
import com.github.azereaSRG.capstone.Menus.MainMenu;
import com.github.azereaSRG.capstone.PlayerScripts.Player;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    private Batch batch;
    private BitmapFont font;
//    private Texture image;

    @Override
    public void create() {
        batch = new SpriteBatch();
//        image = new Texture("libgdx16.png");
        setScreen(new MainMenu(this));
        executeMain();
    }

    @Override
    public void dispose() {
        System.out.println("Stopped Execution of Main");
        batch.dispose();
//        image.dispose();
        super.dispose();
    }


    public static void executeMain() {
        System.out.println("Executing Main...");

        Camera camera = new PerspectiveCamera();
        Viewport viewport = new FitViewport(800,480, camera);

        Player player = new Player(0,0, viewport, new Texture("libgdx16.png"));

        Faceling.Stranger entity = new Faceling.Stranger(0,0,new Texture("libgdx16.png"), player);
        entity.runMain();
    }

    public Batch getBatch() {
        return batch;
    }

    public BitmapFont getFont() {
        return font;
    }
}
