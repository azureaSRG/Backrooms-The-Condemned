package com.github.azereaSRG.capstone;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Player extends GameObject {
    protected static final float SCALE = 1 / 32f;
    protected final Viewport gameViewport;

    //Player Attributes
    protected int health, hunger, sanity, speed, strength, stamina;
    protected int maxHealth, maxHunger, maxSanity, maxSpeed, maxStrength, maxStamina;
    protected float infection;
    protected float infectionResistance;

    //Character Specific Attributes
    protected int rarity;
    protected float meleeAffinity, rangedAffinity;

    public Player(float x, float y, Viewport gameViewport, Texture texture) {
        super(x, y, texture.getWidth() * SCALE, texture.getHeight() * SCALE);
        this.gameViewport = gameViewport;
    }

    @Override
    public void update(float deltaTime) {

    }
}
