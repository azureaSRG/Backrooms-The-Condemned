package com.github.azereaSRG.capstone.PlayerScripts;
import com.github.azereaSRG.capstone.ItemScripts.Item;
import java.util.ArrayList;

public class Inventory {
    Backpack backpack;
    PrimarySlot primarySlot;
    SecondarySlot secondarySlot;
    public Inventory(int backpackWidth, int backpackHeight) {
        backpack = new Backpack(backpackWidth, backpackHeight);
    }

    public Inventory() {
        this(1,1);
    }
}

class PrimarySlot {}
class SecondarySlot {}

class Backpack {
    /*
    [][][][][][][]
    [][][][][][][]
    [][][][][][][]
    [][][][][][][]
    */
    private Item[][] backpack;
    private ArrayList<Item> itemsInBackpack = new ArrayList<Item>();
    private final int width, height;

    public Backpack(int width, int height) {
        backpack = new Item[width][height];
        this.width = width;
        this.height = height;
    }

    private boolean scanBounds(int x, int y, int width, int height) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (backpack[x + i][y + j] != null) {
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
                    backpack[xPos + i][yPos + j] = item;
                }
            }
            itemsInBackpack.add(item);
            return true;
        }
        return false;
    }

    public void printBag() {
        for (Item[] row : backpack) {
            System.out.print("[");
            System.out.print(row[0]);
            int index = 1;
            do {
                System.out.print(", " + row[index]);
                index++;
            } while(index < row.length);
            System.out.println("]");
        }
    }
}
