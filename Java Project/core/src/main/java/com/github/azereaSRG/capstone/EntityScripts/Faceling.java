package com.github.azereaSRG.capstone.EntityScripts;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.github.azereaSRG.capstone.ItemScripts.Item;
import com.github.azereaSRG.capstone.PlayerScripts.Player;

import javax.swing.*;
import javax.swing.text.html.parser.Entity;
import java.util.Enumeration;
import java.util.HashMap;

public class Faceling {

    public static abstract class FacelingInterface extends EntityInterface.EntityClass implements EntityInterface {
        protected boolean isRacist;
        protected float aggression;
        protected int bond;
        protected HashMap<Item, Float> drops = new HashMap<>();

        public FacelingInterface(float x, float y, Texture texture, Player playerRef) {
            super(x, y, texture, playerRef);
            isRacist = (Math.random() < 0.192);
            /*
            Bond Ranges:
            0 - 25: Hostile
            26 - 69: Neutral
            70 - 89: Acquaintance
            90 - 100: Friendly
             */
            bond = isRacist ? (int) (Math.random() * 30) + 20 : (int) (Math.random() * 43) + 26;
            aggression = (float) (isRacist ? 0.5 : 0.15);
        }

        protected boolean playerCloseby() {
            super.getCenter(new Vector2(this.rect.x,this.rect.y));
            return false;
        }
    }

    public static class Stranger extends FacelingInterface {
        public Stranger(float x, float y, Texture texture, Player playerRef) {
            super(x, y, texture, playerRef);
            actionState = ActionStates.WANDERING;
            behaviorState = bond <= 0.25 ? EntityStates.HOSTILE : EntityStates.NEUTRAL;
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
            updateAction();
            runAction();
        }

        private void updateAction() {
            switch(actionState) {
                case WANDERING:
                    break;
                case FLEEING:
                    break;
                case FIGHTING:
                    break;
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
