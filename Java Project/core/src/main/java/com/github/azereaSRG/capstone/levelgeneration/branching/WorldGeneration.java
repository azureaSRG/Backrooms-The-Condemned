package com.github.azereaSRG.capstone.levelgeneration.branching;

import com.github.czyzby.kiwi.util.tuple.immutable.Pair;
import java.util.ArrayList;
import java.util.Random;

public class WorldGeneration {
    static int maxWidth = 100;
    static int maxHeight = 100;
    static int criticalPathLength = 50;
    static ArrayList<Pair<Integer, Integer>> startPositions = new ArrayList<>();
    static int branchNum = 0;
    static int branchLength = 0;
    static Random rand = new Random();

    static Pair<Integer, Integer> startLocation;
    public static void createWorld() {
        startLocation = startPositions.isEmpty() ? new Pair<>(
            rand.nextInt(maxWidth), rand.nextInt(maxHeight)
        ) : startPositions.get(rand.nextInt(startPositions.size()-1));


    }

    public static void generateCriticalPath() {

    }
}
