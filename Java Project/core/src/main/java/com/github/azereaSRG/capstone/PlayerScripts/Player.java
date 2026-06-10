package com.github.azereaSRG.capstone.PlayerScripts;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.azereaSRG.capstone.GameObject;
import com.github.azereaSRG.capstone.ItemScripts.Item;
import com.badlogic.gdx.math.Vector2;

public class Player extends GameObject {
    protected static final float SCALE = 1 / 256f;
    protected final Viewport gameViewport;

    //Player Attributes
    protected int health, hunger, sanity, speed, strength, stamina;
    protected int maxHealth, maxHunger, maxSanity, maxSpeed, maxStrength, maxStamina;
    protected float infection;
    protected float infectionResistance;

    //Character Specific Attributes
    protected int rarity;
    protected float meleeAffinity, rangedAffinity;

    private Inventory inventory;

    private final Vector2 moveDirection = new Vector2(0, 0);

    public Player(float x, float y, Viewport gameViewport, Texture texture) {
        super(x, y, texture.getWidth() * SCALE, texture.getHeight() * SCALE, texture);
        this.gameViewport = gameViewport;
        inventory = new Inventory(5, 5);

        //TESTING PURPOSES
        setAttributesToStandard();
    }

    public void setAttributesToStandard() {
        maxSpeed = 2;
        speed = maxSpeed;
        accessInventory();
    }

    private void testInventory() {
//        inventory.backpack.addItem();
    }

    public void reset(float x, float y) {
        rect.setPosition(x, y);
        health = maxHealth;
    }

    @Override
    public void update(float deltaTime) {
        move(deltaTime);
    }

    public void useItem(Item item) {
        item.useItem();
    }

    private void move(float deltaTime) {
        if (moveDirection.isZero()) return; //Ends method early

        float xDir = rect.getX() + moveDirection.x * speed * deltaTime;
        float yDir = rect.getY() + moveDirection.y * speed * deltaTime;

        xDir = MathUtils.clamp(xDir, 0, gameViewport.getWorldWidth() - rect.getWidth());
        yDir = MathUtils.clamp(yDir, 0, gameViewport.getWorldHeight() - rect.getHeight());

        rect.setPosition(xDir, yDir);
    }


    public void updateDirection(Vector2 newDirection) {
        moveDirection.set(newDirection);
    }

    private void killPlayer() {
        texture.dispose();
    }

    public void damage(int amount) {
        health -= amount;
        if (health <= 0) killPlayer();
        System.out.println("Player Killed");
    }

    public void heal(int amount) {
        health = Math.min(health + amount, maxHealth);
    }

    public void accessInventory() {
        System.out.println("Inventory Accessed");
        inventory.backpack.printBag();
    }
}
