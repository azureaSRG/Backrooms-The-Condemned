package com.github.azereaSRG.capstone.playerutil;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.azereaSRG.capstone.GameObject;
import com.badlogic.gdx.math.Vector2;
import com.github.azereaSRG.capstone.itemutil.Item;
import com.github.azereaSRG.capstone.itemutil.ItemCreator;
import com.github.azereaSRG.capstone.Tag;


public class Player extends GameObject {
    protected static final float SCALE = 1 / 265f;
    protected final Viewport gameViewport;

    //Player Attributes
    protected int health, hunger, sanity, thirst, speed, strength, stamina;
    protected int maxHealth, maxHunger, maxThirst, maxSanity, maxSpeed, maxStrength, maxStamina;
    protected int baseSpeed, baseStrength;
    protected int speedBuffDuration, strengthBuffDuration;
    protected float infection;
    protected float infectionResistance;

    //Character Specific Attributes
    protected int rarity;
    protected float meleeAffinity, rangedAffinity;

    private Inventory inventory;

    private final Vector2 moveDirection = new Vector2(0, 0);

    public Player(float x, float y, Viewport gameViewport, Texture texture) {
        super(x, y, texture.getWidth() * SCALE, texture.getHeight() * SCALE, texture);

        this.tag = Tag.PLAYER;

        inventory = new Inventory(5, 5);

        //TESTING PURPOSES
        System.out.println("Viewport passed to player: " + gameViewport);
        this.gameViewport = gameViewport;
        setAttributesToStandard();
    }

    public void setAttributesToStandard() {
        maxSpeed = 10;
        this.maxHealth = 100;
        this.health = 100;
        speed = maxSpeed;
        testInventory();
    }

    private void testInventory() {
        inventory.backpack.addItem(ItemCreator.createItem("item:almond_water"),0,0);
        inventory.backpack.addItem(ItemCreator.createItem("item:ramen_pack"),2,2);
        inventory.backpack.addItem(ItemCreator.createItem("item:water_bottle"),1,0);

//        accessInventory();
//        useItem(0, 0);
//        accessInventory();
    }

    public void reset(float x, float y) {
        rect.setPosition(x, y);
        System.out.println("RESETTING PLAYER");
        health = maxHealth;
    }

    @Override
    public void update(float deltaTime) {
        move(deltaTime);
    }


    public void useItem(int leftBound, int topBound) {
        Item item = inventory.backpack.removeItem(leftBound, topBound);
        item.useItem(this);
        System.out.println("Used " + item.getName());

    }

    private void move(float deltaTime) {
        if (moveDirection.isZero()) return; //Ends method early

        float xDir = rect.getX() + moveDirection.x * speed * deltaTime;
        float yDir = rect.getY() + moveDirection.y * speed * deltaTime;

        xDir = MathUtils.clamp(xDir, 0, gameViewport.getWorldWidth() - rect.getWidth());
        yDir = MathUtils.clamp(yDir, 0, gameViewport.getWorldHeight() - rect.getHeight());

        rect.setPosition(xDir, yDir);
//        System.out.println("MOVE: " + rect.x + ", " + rect.y);

    }

    public void updateDirection(Vector2 newDirection) {
        moveDirection.set(newDirection);
    }

    private void killPlayer() {
        System.out.println("Player Killed");
        texture.dispose();
    }

    public void damage(int amount) {
        health -= amount;
        System.out.println("Player Health: " + health);
        if (health <= 0) killPlayer();

    }

    public void heal(int amount) {
        health += amount;
        health = Math.min(health, maxHealth);
        System.out.println("Player healed for " + health + " health!");
        System.out.println("Health: " + health);
    }

    public void addHunger(int amount) {
        hunger = Math.min(hunger + amount, maxHunger);
    }

    public void addThirst(int amount) {
        thirst = Math.min(thirst + amount, maxThirst);
    }

    public void addSanity(int amount) {
        sanity = Math.min(sanity + amount, maxSanity);
    }

    public void addStamina (int amount) {
        stamina = Math.min(stamina + amount , maxStamina);
    }

    public void addSpeedBuff(int amount, int duration) {
        maxSpeed += amount;
        speedBuffDuration += duration;
    }

    public void addStrengthBuff(int amount, int duration) {
        maxStrength += amount;
        strengthBuffDuration += duration;
    }

    public void setSpeedBase() {
        maxSpeed = baseSpeed;
        speed = Math.min(speed, maxSpeed);
    }

    public void setStrengthBase() {
        maxStrength = baseStrength;
        strength = Math.min(strength, maxStrength);
    }

    public void setInfectionZero() {
        infection = 0;
    }

    public void setInfection(float infectionAmount) {
        infection = infectionAmount;
    }

    public void accessInventory() {
//        System.out.println("Inventory Accessed");
        inventory.backpack.printBag();
    }

    public float getX() {
        return this.rect.x;
    }

    public float getY() {
        return this.rect.y;
    }
}
