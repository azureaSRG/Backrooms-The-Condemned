package com.github.azereaSRG.capstone.levelgeneration.wavefunctioncollapse;

public class InvalidNode extends RuntimeException {
    public InvalidNode(String message) {
        super(message);
    }

    public InvalidNode(String message, Throwable throwable) {
        super(message, throwable);
    }
}
