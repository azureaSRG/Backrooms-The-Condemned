package com.github.azereaSRG.capstone.EntityScripts;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.azereaSRG.capstone.Player;

public class Faceling extends EntityInterface.EntityClass implements EntityInterface {

    EntityStates state = EntityStates.NEUTRAL;

    public Faceling(float x, float y, Texture texture, Player player) {
        super(x, y, texture, player);
    }

    public void runMain() {
        System.out.println("Entity Object Created!");
        System.out.println(this.getHealth());
        System.out.println();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
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
}
