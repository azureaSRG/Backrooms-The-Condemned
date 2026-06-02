package com.github.azereaSRG.capstone.ItemScripts;

import com.github.azereaSRG.capstone.Player;

public class PlayerConsumable extends PlayerItem {
    protected int health, hunger, thirst, sanity, speed, strength, stamina;

    public PlayerConsumable(String name, String description, int rarity,
                            int itemWidthInInventory, int itemHeightInInventory) {
        super(name, description, rarity, itemWidthInInventory, itemHeightInInventory);
    }

    public PlayerConsumable(String name, String description, int rarity) {
        this(name, description, rarity, 1, 1);
    }
}
