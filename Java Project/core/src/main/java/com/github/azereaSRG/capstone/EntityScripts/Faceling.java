package com.github.azereaSRG.capstone.EntityScripts;

import com.badlogic.gdx.graphics.Texture;
import com.github.azereaSRG.capstone.PlayerScripts.Player;

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
        public void update(float deltaTime) {
            super.update(deltaTime);
        }

        public void runMain() {
            System.out.println(this.health);
        }
    }
    public static class Butcher extends FacelingInterface {
        public Butcher(float x, float y, Texture texture, Player playerRef) {
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
    }
    public static class Shopkeeper extends FacelingInterface {
        public Shopkeeper(float x, float y, Texture texture, Player playerRef) {
            super(x, y, texture, playerRef);
        }

        public void trade() {

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
    }
    public static class PoliceOfficer extends FacelingInterface {
        public PoliceOfficer(float x, float y, Texture texture, Player playerRef) {
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

    }
    public static class MilitaryGeneral extends FacelingInterface {
        public MilitaryGeneral(float x, float y, Texture texture, Player playerRef) {
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
    }
}
