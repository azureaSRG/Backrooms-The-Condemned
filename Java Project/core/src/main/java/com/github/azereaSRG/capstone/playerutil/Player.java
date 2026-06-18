package com.github.azereaSRG.capstone.playerutil;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.azereaSRG.capstone.GameObject;
import com.badlogic.gdx.math.Vector2;
import com.github.azereaSRG.capstone.gamemenus.GameScreen;
import com.github.azereaSRG.capstone.itemutil.Item;
import com.github.azereaSRG.capstone.itemutil.ItemCreator;
import com.github.azereaSRG.capstone.Tag;
import com.github.azereaSRG.capstone.levelgeneration.wavefunctioncollapse.World;

import java.util.ArrayList;

public class Player extends GameObject {
    private enum Direction {
        DOWN,
        UP,
        LEFT,
        RIGHT
    }


    protected static final float SCALE = 1 / 512f;
    protected final Viewport gameViewport;

    protected Direction facing = Direction.DOWN;
    private Animation<TextureRegion> walkFront, walkBack, walkLeft, walkRight;
    private Animation<TextureRegion> currentAnimation;
    private float stateTime = 0f;

    //Player Attributes
    protected int health, hunger, sanity, thirst, speed, strength, stamina;
    protected int maxHealth, maxHunger, maxThirst, maxSanity, maxSpeed, maxStrength, maxStamina;
    protected int walkingSpeed, sprintSpeed, baseStrength;
    protected int speedBuffDuration, strengthBuffDuration;
    protected float infection;
    protected float infectionResistance;

    //Character Specific Attributes
    protected int rarity;
    protected float meleeAffinity, rangedAffinity;

    private Inventory inventory;

    private final Vector2 moveDirection = new Vector2(0, 0);
    private final float xBound, yBound;
    private ArrayList<Rectangle> wallBounds;

    public Player(float x, float y, Viewport gameViewport, Texture front, Texture back,
                  Texture left, Texture right, float xBound, float yBound) {
        super(x, y, getFrameWidth(front, 4), front.getHeight() * SCALE, front);

        walkFront = createAnimation(front, 4);
        walkBack  = createAnimation(back,  3);
        walkLeft  = createAnimation(left,  3);
        walkRight = createAnimation(right, 3);

        currentAnimation = walkFront;

        currentAnimation = walkFront;

        this.xBound = xBound;
        this.yBound = yBound;
        this.tag = Tag.PLAYER;

        inventory = new Inventory(5, 5);

        //TESTING PURPOSES
//        System.out.println("Viewport passed to player: " + gameViewport);
        this.gameViewport = gameViewport;
        setAttributesToStandard();
    }

    public void setAttributesToStandard() {
        maxSpeed = 6;
        sprintSpeed = maxSpeed;
        walkingSpeed = Math.floorDiv(maxSpeed, 3);
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

    private Animation<TextureRegion> createAnimation(Texture sheet, int frameCount) {
        int frameWidth  = sheet.getWidth() / frameCount;
        int frameHeight = sheet.getHeight();
        TextureRegion[][] frames = TextureRegion.split(sheet, frameWidth, frameHeight);
        return new Animation<>(0.15f, frames[0]);
    }

    private static float getFrameWidth(Texture sheet, int frameCount) {
        return (sheet.getWidth() / (float) frameCount) * SCALE;
    }

    public void reset(float x, float y) {
        rect.setPosition(x, y);
        System.out.println("RESETTING PLAYER");
        health = maxHealth;
    }

    @Override
    public void update(float deltaTime) {
        boolean isSprinting = (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)); // sprint testing
        move(deltaTime, isSprinting);
        if (!moveDirection.isZero()) {
            stateTime += deltaTime;
        } else {
            stateTime = 0f; // reset to first frame when idle
        }
    }


    public void useItem(int leftBound, int topBound) {
        Item item = inventory.backpack.removeItem(leftBound, topBound);
        item.useItem(this);
        System.out.println("Used " + item.getName());

    }

    private void move(float deltaTime, boolean isSprinting) {
        if (moveDirection.isZero()) return;

        int movementSpeed = isSprinting ? sprintSpeed : walkingSpeed;

        float newX = rect.getX() + moveDirection.x * movementSpeed * deltaTime;
        float newY = rect.getY() + moveDirection.y * movementSpeed * deltaTime;

//        newX = MathUtils.clamp(newX, 0, xBound - rect.getWidth());
//        newY = MathUtils.clamp(newY, 0, yBound - rect.getHeight());

        Rectangle testX = new Rectangle(newX, rect.getY(), rect.getWidth(), rect.getHeight());
        Rectangle testY = new Rectangle(rect.getX(), newY, rect.getWidth(), rect.getHeight());

        boolean blockX = false;
        boolean blockY = false;

        if (wallBounds != null) {
            for (Rectangle wall : wallBounds) {
                if (testX.overlaps(wall)) blockX = true;
                if (testY.overlaps(wall)) blockY = true;
                if (blockX && blockY) break;
            }
        }

        if (!blockX) rect.setX(newX);
        if (!blockY) rect.setY(newY);

        checkDuration(deltaTime);
    }

    private void checkDuration(float deltaTime) {
        speedBuffDuration -= deltaTime;
        strengthBuffDuration -= deltaTime;
        if (strengthBuffDuration < 0) strength = baseStrength;
        if (speedBuffDuration < 0) {
            maxSpeed = sprintSpeed;
            walkingSpeed = Math.floorDiv(sprintSpeed, 3); // 1/3 speed
            speed = maxSpeed;
        }
    }

    public void updateDirection(Vector2 newDirection) {
        moveDirection.set(newDirection);

        if (!newDirection.isZero()) {
            if (Math.abs(newDirection.x) > Math.abs(newDirection.y)) {
                facing = newDirection.x > 0 ? Direction.RIGHT : Direction.LEFT;
            } else {
                facing = newDirection.y > 0 ? Direction.UP : Direction.DOWN;
            }
        }

        // Update current animation based on facing
        switch (facing) {
            case UP:    currentAnimation = walkBack;  break;
            case DOWN:  currentAnimation = walkFront; break;
            case LEFT:  currentAnimation = walkLeft;  break;
            case RIGHT: currentAnimation = walkRight; break;
        }
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
        maxSpeed = sprintSpeed;
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

    public void accessSlot(String slot) {
        Item item = inventory.primarySlot.item;
    }

    public float getX() {
        return this.rect.x;
    }

    public float getY() {
        return this.rect.y;
    }

    public void setDirection(int direction) {
        switch (direction) {
            case 0:
                facing = Direction.RIGHT;
                break;
            case 1:
                facing = Direction.UP;
                break;
            case 2:
                facing = Direction.LEFT;
                break;
            case 3:
                facing = Direction.DOWN;
                break;
        }
    }

    public void setWallBounds(ArrayList<Rectangle> wallBounds) {
        this.wallBounds = wallBounds;
    }


    @Override
    public void draw(Batch batch) {
        TextureRegion frame = currentAnimation.getKeyFrame(stateTime, true); // true = loop
        batch.draw(frame, rect.x, rect.y, rect.width, rect.height);
//        System.out.println("Player rect: " + rect.x + ", " + rect.y +
//            " size: " + rect.width + "x" + rect.height);
    }
}
