package ru.makar.cource.project.gp.data;

import java.util.Arrays;

public enum Directions {
    UP(0, -1),
    RIGHT(1, 0),
    DOWN(0, 1),
    LEFT(-1, 0);

    int xOffset;
    int yOffset;

    Directions(int xOffset, int yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public int getXOffset() {
        return xOffset;
    }

    public int getYOffset() {
        return yOffset;
    }

    public Directions turnRight() {
        return values()[(this.ordinal() + 1) % values().length];
    }

    public Directions turnLeft() {
        return values()[(this.ordinal() - 1) % values().length];
    }
}
