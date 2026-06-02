package com.github.azereaSRG.capstone.ItemScripts;

public abstract class PlayerItem {
    protected String name;
    protected String description;
    protected int rarity;
    protected int itemWidthInInventory, itemHeightInInventory;

    public PlayerItem(String name, String description, int rarity) {
        this(name, description, rarity, 1, 1);
    }

    public PlayerItem(String name, String description, int rarity,
                      int itemWidthInInventory, int itemHeightInInventory) {
        this.name = name;
        this.description = description;
        this.rarity = rarity;
        this.itemHeightInInventory = itemHeightInInventory;
        this.itemWidthInInventory = itemWidthInInventory;
    }

    public void draw() {

    }
}
