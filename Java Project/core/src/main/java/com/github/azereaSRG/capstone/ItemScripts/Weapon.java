package com.github.azereaSRG.capstone.ItemScripts;

import com.github.azereaSRG.capstone.ItemScripts.Item;

public class Weapon extends Item {
    private float damage, range, firingInterval, handling, durability, weight;
    private boolean isMelee;

    public Weapon(String name, String description, int rarity,
                  int itemWidthInInventory, int itemHeightInInventory) {
        super(name, description, rarity, itemWidthInInventory, itemHeightInInventory);
    }

    public Weapon(String name, String description, int rarity) {
        this(name, description, rarity, 1, 1);
    }

    public void attack() {

    }
}
