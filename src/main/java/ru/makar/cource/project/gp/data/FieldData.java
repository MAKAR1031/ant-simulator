package ru.makar.cource.project.gp.data;

import ec.gp.GPData;
import lombok.Data;

@Data
public class FieldData extends GPData{
    private boolean field[][];
    private int n;
    private Position position;
    private int counter;
    private int food;
    private int maxFood;

    @Override
    public void copyTo(GPData gpData) {
        FieldData data = (FieldData) gpData;
        data.field = this.field;
        data.n = this.n;
        data.position = this.position;
        data.counter = this.counter;
        data.food = this.food;
        data.maxFood = this.maxFood;
    }
}
