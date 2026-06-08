package com.github.azereaSRG.capstone.ItemScripts;

public class Item {
    protected String name;
    protected String description;
    protected int rarity;
    protected int itemWidthInInventory, itemHeightInInventory;

    public Item(String name, String description, int rarity) {
        this(name, description, rarity, 1, 1);
    }

    public Item(String name, String description, int rarity,
                int itemWidthInInventory, int itemHeightInInventory) {
        this.name = name;
        this.description = description;
        this.rarity = rarity;
        this.itemHeightInInventory = itemHeightInInventory;
        this.itemWidthInInventory = itemWidthInInventory;
    }

    public int getItemWidth() {
        return itemWidthInInventory;
    }

    public int getItemHeight() {
        return itemHeightInInventory;
    }

    public void draw() {
        
    }

    public void dropItem() {
        
    }
    
    public void useItem() {}

    static class Icon {
        private Texture texture;
    }
}

