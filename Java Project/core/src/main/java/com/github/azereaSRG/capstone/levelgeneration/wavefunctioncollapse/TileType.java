package com.github.azereaSRG.capstone.levelgeneration.wavefunctioncollapse;

public enum TileType {

    //Road Tiles
    VERTICAL(
        Port.ROAD, Port.ROAD, Port.GRASS, Port.GRASS, 20, "|"
    ),
    HORIZONTAL(
        Port.GRASS, Port.GRASS, Port.ROAD, Port.ROAD, 20, "-"
    ),
    L_NW(
        Port.GRASS, Port.ROAD, Port.GRASS, Port.ROAD, 9,"L"
    ),
    L_NE(
        Port.GRASS, Port.ROAD, Port.ROAD, Port.GRASS, 9,"L"
    ),
    L_SW(
        Port.ROAD, Port.GRASS, Port.GRASS, Port.ROAD,9,"L"
    ),
    L_SE(
        Port.ROAD, Port.GRASS, Port.ROAD, Port.GRASS,9,"L"
    ),
    T_N (
        Port.ROAD, Port.GRASS, Port.ROAD, Port.ROAD,5,"T"
    ),
    T_E (
        Port.ROAD, Port.ROAD, Port.GRASS, Port.ROAD,5,"T"
    ),
    T_S (
        Port.GRASS, Port.ROAD, Port.ROAD, Port.ROAD,5,"T"
    ),
    T_W (
        Port.ROAD, Port.ROAD, Port.ROAD, Port.GRASS,5,"T"
    ),
    PLUS(
        Port.ROAD, Port.ROAD, Port.ROAD, Port.ROAD,10,"+"
    ),
    DEAD_N(
        Port.ROAD, Port.GRASS, Port.GRASS, Port.GRASS,1,"N"
    ),
    DEAD_E(
        Port.GRASS, Port.GRASS, Port.GRASS, Port.ROAD,1,"E"
    ),
    DEAD_S(
        Port.GRASS, Port.ROAD, Port.GRASS, Port.GRASS,1,"S"
    ),
    DEAD_W(
        Port.GRASS, Port.GRASS, Port.ROAD, Port.GRASS,1,"W"
    ),

    //Non-Road Tiles
    EMPTY(
        Port.GRASS, Port.GRASS, Port.GRASS, Port.GRASS,20," "
    ),
    BUILDING(
        Port.GRASS, Port.GRASS, Port.GRASS, Port.GRASS,1,"B"
    ),
    BACKUP(
        Port.GRASS,Port.GRASS, Port.GRASS, Port.GRASS, 1, "?"
    );


    public final Port top;
    public final Port bottom;
    public final Port left;
    public final Port right;
    public final int weight;
    public final String symbol;

    TileType(Port top, Port bottom, Port left, Port right, int weight, String symbol) {
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
        this.weight = weight;
        this.symbol = symbol;
    }
}
