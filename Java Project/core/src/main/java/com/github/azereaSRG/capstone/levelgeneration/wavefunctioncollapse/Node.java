package com.github.azereaSRG.capstone.levelgeneration.wavefunctioncollapse;

import com.github.azereaSRG.capstone.GameObject;
import java.util.ArrayList;

public class Node {
    String name;
    GameObject objectProperties; //GameObject
    protected ConnectionType top;
    protected ConnectionType bottom;
    protected ConnectionType left;
    protected ConnectionType right;
}

/**
 * Straight: 2 [Up -> Down; Left -> Right]
 * Curve: 4 [Bottom -> Right; Right -> Up; Up -> Left; Left -> Bottom]
 * In-Between: 1 [Nothing]
 */

class StraightNode{
    static class Horizontal extends Node {
        public Horizontal() {
            name = "-";
            top = ConnectionType.GRASS;
            bottom = ConnectionType.GRASS;
            left = ConnectionType.ROAD;
            right = ConnectionType.ROAD;
        }
    }
    static class Vertical extends Node{
        public Vertical() {
            name = "|";
            top = ConnectionType.ROAD;
            bottom = ConnectionType.ROAD;
            left = ConnectionType.GRASS;
            right = ConnectionType.GRASS;
        }
    }
}

class CurvedNode extends Node {
    static class LPiece extends Node {
        public LPiece() {
            name = "|_";
            top = ConnectionType.ROAD;
            right = ConnectionType.ROAD;
            bottom = ConnectionType.GRASS;
            left = ConnectionType.GRASS;
        }
    }
    static class RPiece extends Node {
        public RPiece() {
            name = "|-";
            top = ConnectionType.GRASS;
            right = ConnectionType.ROAD;
            bottom = ConnectionType.ROAD;
            left = ConnectionType.GRASS;
        }
    }
    static class MLPiece extends Node {
        public MLPiece() {
            name = "_|";
            top = ConnectionType.ROAD;
            right = ConnectionType.GRASS;
            bottom = ConnectionType.GRASS;
            left = ConnectionType.ROAD;
        }
    }
    static class MRPiece extends Node {
        public MRPiece() {
            name = "-|";
            top = ConnectionType.GRASS;
            right = ConnectionType.GRASS;
            bottom = ConnectionType.ROAD;
            left = ConnectionType.ROAD;
        }
    }
}

class EmptyNode {
    static class Empty extends Node {
        public Empty() {
            name = "X";
            top = ConnectionType.GRASS;
            bottom = ConnectionType.GRASS;
            left = ConnectionType.GRASS;
            right = ConnectionType.GRASS;
        }
    }
}

class BranchNode {
    static class TPieceU extends Node {
        public TPieceU() {
            name = "";
            top = ConnectionType.ROAD;
            bottom = ConnectionType.GRASS;
            left = ConnectionType.ROAD;
            right = ConnectionType.ROAD;
        }
    }
    static class TPieceR extends Node {
        public TPieceR() {
            name = "";
            top = ConnectionType.ROAD;
            bottom = ConnectionType.ROAD;
            left = ConnectionType.GRASS;
            right = ConnectionType.ROAD;
        }
    }
    static class TPieceB extends Node {
        public TPieceB() {
            name = "T";
            top = ConnectionType.GRASS;
            bottom = ConnectionType.ROAD;
            left = ConnectionType.ROAD;
            right = ConnectionType.ROAD;
        }
    }
    static class TPieceL extends Node {
        public TPieceL() {
            name = "";
            top = ConnectionType.ROAD;
            bottom = ConnectionType.ROAD;
            left = ConnectionType.ROAD;
            right = ConnectionType.GRASS;
        }
    }
    static class Plus extends Node {
        public Plus() {
            name = "+";
            top = ConnectionType.ROAD;
            bottom = ConnectionType.ROAD;
            left = ConnectionType.ROAD;
            right = ConnectionType.ROAD;
        }
    }
}
