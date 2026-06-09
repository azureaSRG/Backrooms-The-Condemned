package com.github.azereaSRG.capstone.PlayerScripts;
import com.github.azereaSRG.capstone.ItemScripts.Item;
import java.util.ArrayList;

public class Inventory {
    public Inventory(int backpackWidth, int backpackHeight) {

    }

    public Inventory() {
        this(1,1);
    }

    static class Backpack {
        /*
        [][][][][][][]
        [][][][][][][]
        [][][][][][][]
        [][][][][][][]
        */
        private Item[][] inventory;
        private ArrayList<Item> itemsInBackpack = new ArrayList<Item>();
        private final int width, height;

        public Backpack(int width, int height) {
            inventory = new Item[width][height];
            this.width = width;
            this.height = height;
        }

        private boolean scanBounds(int x, int y, int width, int height) {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    if (inventory[x + i][y + j] != null) {
                        return false;
                    }
                }
            }
            return true;
        }

        public boolean addItem(Item item, int xPos, int yPos) {
            int itemWidth = item.getItemWidth();
            int itemHeight = item.getItemHeight();

            if (scanBounds(xPos, yPos, itemWidth, itemHeight)) {
                for (int i = 0; i < itemWidth; i++) {
                    for (int j = 0; j < itemHeight; j++) {
                        inventory[xPos + i][yPos + j] = item;
                    }
                }
                itemsInBackpack.add(item);
                return true;
            }
            return false;
        }
    }
    static class PrimarySlot {
        Item item;
    }
    static class SecondarySlot {
        Item item;
    }
}
