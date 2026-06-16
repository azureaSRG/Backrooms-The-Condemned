package com.github.azereaSRG.capstone.levelgeneration.wavefunctioncollapse;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Connection {
    ArrayList<Class<? extends Node>> compatibleNodes;

    public Connection() {
        compatibleNodes = new ArrayList<>();
    }

    public Connection(Class<? extends Node>... nodeClasses) {
        compatibleNodes = new ArrayList<>();
        compatibleNodes.addAll(Arrays.asList(nodeClasses));
    }

    public List<Class<? extends Node>> getCompatibleNodes() {
        return compatibleNodes;
    }
}
