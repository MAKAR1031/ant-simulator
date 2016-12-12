package ru.makar.cource.project.gp.data;

public enum Directions {
    UP(0, -1),
    RIGHT(1, 0),
    DOWN(0, 1),
    LEFT(-1, 0);

    int colOffset;
    int rowOffset;

    Directions(int colOffset, int yOffset) {
        this.colOffset = colOffset;
        this.rowOffset = yOffset;
    }

    public int getColOffset() {
        return colOffset;
    }

    public int getRowOffset() {
        return rowOffset;
    }

    public Directions turn(int spin) {
        return values()[Math.abs(this.ordinal() + spin) % values().length];
    }

    public Directions turnRight() {
        return turn(1);
    }

    public Directions turnLeft() {
        return turn(-1);
    }

    public Directions turnAround() {
        return turn(2);
    }
}
