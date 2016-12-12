package ru.makar.cource.project.ui;

import javafx.beans.property.SimpleIntegerProperty;

public class TableFoodData {
    private final SimpleIntegerProperty x;
    private final SimpleIntegerProperty y;

    public TableFoodData(int x, int y) {
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
    }

    public int getX() {
        return x.get();
    }

    public int getY() {
        return y.get();
    }

    public void set(int value, String property) {
        switch (property) {
            case "x":
                this.x.set(value);
                break;
            case "y":
                this.y.set(value);
        }
    }
}
