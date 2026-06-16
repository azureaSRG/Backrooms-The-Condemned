package com.github.azereaSRG.capstone.levelgeneration.wavefunctioncollapse;

public class NoCompatibleNodes extends RuntimeException {
    public NoCompatibleNodes(String message) {
//        super(message);
        System.out.print("NoCompatibleNodesException: " + message);
    }
}
