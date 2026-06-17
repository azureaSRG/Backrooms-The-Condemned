package com.github.azereaSRG.capstone.itemutil;

import com.github.azereaSRG.capstone.playerutil.Player;

public class Consumable extends Item {
    private int health, hunger, thirst, sanity, speed, strength, stamina;

    public Consumable(String name, String description, String identifier,
                      int rarity, int itemWidthInInventory, int itemHeightInInventory,
                      int health, int hunger, int thirst, int sanity, int speed, int strength, int stamina
                      ) {
        this(name, description, rarity, itemWidthInInventory, itemHeightInInventory);
        this.identifier = identifier;
        this.health = health;
        this.hunger = hunger;
        this.sanity = sanity;
        this.speed = speed;
        this.stamina = stamina;

    }

    public Consumable(String name, String description, int rarity,
                      int itemWidthInInventory, int itemHeightInInventory) {
        super(name, description, rarity, itemWidthInInventory, itemHeightInInventory);
    }

    @Override
    public void useItem(Player playerRef) {
        playerRef.heal(health);
        playerRef.addHunger(hunger);
        playerRef.addThirst(thirst);
        playerRef.addSanity(sanity);
        playerRef.addStamina(stamina);
    }
}
