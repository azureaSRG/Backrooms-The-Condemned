package com.github.azereaSRG.capstone.EntityScripts;

import com.badlogic.gdx.graphics.Texture;
import com.github.azereaSRG.capstone.ItemScripts.Item;
import com.github.azereaSRG.capstone.PlayerScripts.Player;

import javax.swing.*;
import java.util.Enumeration;
import java.util.HashMap;

public class Faceling {

    public static abstract class FacelingInterface extends EntityInterface.EntityClass implements EntityInterface {
        protected boolean isRacist;
        protected HashMap<Item, Float> drops = new HashMap<>();

        public FacelingInterface(float x, float y, Texture texture, Player playerRef) {
            super(x, y, texture, playerRef);
            isRacist = (Math.random() < 0.192);
        }
    }

    public static class Stranger extends FacelingInterface {
        public Stranger(float x, float y, Texture texture, Player playerRef) {
            super(x, y, texture, playerRef);
            actionState = ActionStates.WANDERING;
            behaviorState = EntityStates.NEUTRAL;
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
            updateAction();
            runAction();
        }

        private void updateAction() {
            switch(actionState) {
                case WANDERING:
                case FLEEING:
                case FIGHTING:
            }
        }

        private void runAction() {

        }

        public void takeDamage(int amount) {
            if (behaviorState.ordinal() != 4) {behaviorState = EntityStates.HOSTILE;}
            this.health -= amount;
            if (this.health <= 0) {killEntity();}
        }

        private void killEntity() {

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
