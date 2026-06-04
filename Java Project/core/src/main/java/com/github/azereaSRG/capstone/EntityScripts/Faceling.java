package com.github.azereaSRG.capstone.EntityScripts;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.azereaSRG.capstone.Player;

public class Faceling {

    public static abstract class FacelingInterface extends EntityInterface.EntityClass implements EntityInterface {
        protected boolean isRacist;

        public FacelingInterface(float x, float y, Texture texture, Player playerRef) {
            super(x, y, texture, playerRef);
        }
    }

    public static class Stranger extends FacelingInterface {
        public Stranger(float x, float y, Texture texture, Player playerRef) {
            super(x, y, texture, playerRef);
        }

        @Override
        public void attack() {

        }

        @Override
        public void flee() {

        }

        @Override
        public void wander() {

        }

        @Override
        public EntityClass spawn(Viewport viewport, Texture texture, Player player) {
            return null;
        }

        public void runMain() {
            System.out.println(this.health);
        }
    }
}
