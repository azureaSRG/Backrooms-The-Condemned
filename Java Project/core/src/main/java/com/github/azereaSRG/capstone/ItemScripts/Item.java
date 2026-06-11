package com.github.azereaSRG.capstone.ItemScripts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class Item {
    protected String name;
    protected String description;
    protected String identifier;
    protected int rarity;
    protected int itemWidthInInventory, itemHeightInInventory;

    public Item() {
        this(null,null,0);
    }

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

    protected void pullDataFromJson() {
        Json json = new Json();
        json.setSerializer(Item.class, new Json.Serializer<Item>() {
            @Override
            public void write(Json json, Item object, Class knownType) {

            }

            @Override
            public Item read(Json json, JsonValue jsonData, Class type) {
                Item item = new Item();
                item.name = jsonData.child().name();

                return item;
            }
        });

        json.setElementType(Item.class, "item:one_time_buffs",Item.class);
    }

    @Override
    public String toString() {
        return name;
    }

    public void printItem() {
        System.out.println("ID: " + identifier);
        System.out.println("Name: " + name);
    }
}
