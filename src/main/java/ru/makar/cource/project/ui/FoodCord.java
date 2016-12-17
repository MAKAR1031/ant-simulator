package ru.makar.cource.project.ui;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class FoodCord {
    private int x;
    private int y;

    public FoodCord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void set(int value, String property) {
        switch (property) {
            case "x":
                this.x = value;
                break;
            case "y":
                this.y = value;
        }
    }
}
