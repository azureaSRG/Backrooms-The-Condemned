package com.github.azereaSRG.capstone.ItemScripts;

public class Consumable extends Item {
    private int health, hunger, thirst, sanity, speed, strength, stamina;

    public Consumable(String name, String description, int rarity,
                      int itemWidthInInventory, int itemHeightInInventory) {
        super(name, description, rarity, itemWidthInInventory, itemHeightInInventory);
    }

    public Consumable(String name, String description, int rarity) {
        this(name, description, rarity, 1, 1);
    }

    @Override
    public void useItem() {
        
    }
}
