package com.github.azereaSRG.capstone.levelgeneration.wavefunctioncollapse;

import com.badlogic.gdx.math.Vector2;

import java.util.*;

public class Cell {

    public Set<TileType> possibilities = new HashSet<>();
    public TileType collapsed = null;

    public boolean isCollapsed() {
        return collapsed != null;
    }

    public void collapse(Random rand) {

        if(possibilities.isEmpty()) {
            throw new RuntimeException(
                "Attempted to collapse cell with 0 possibilities"
            );
        }

        int index = weightedRandom(rand);

        collapsed =
            new ArrayList<>(possibilities).get(index);

        possibilities.clear();
        possibilities.add(collapsed);
    }

    public int entropy() {
        return possibilities.size();
    }

    private int weightedRandom(Random rand) {
        ArrayList<TileType> indexed = new ArrayList<>(possibilities);
            int totalWeight = 0;
            for (TileType tile : indexed) {
                totalWeight += tile.weight;
            }
            int random = rand.nextInt(totalWeight);
            int current = 0;
            for (int i = 0; i < indexed.size(); i++) {
                int weight = indexed.get(i).weight;
                if ((random >= current) && random < (weight + current)) {
                    return i;
                }
                current += weight;
            } return 0;
    }
}


