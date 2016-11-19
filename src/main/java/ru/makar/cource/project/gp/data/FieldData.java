package ru.makar.cource.project.gp.data;

import ec.gp.GPData;
import lombok.Data;

@Data
public class FieldData extends GPData{
    private boolean food[][];
    private boolean pheromones[][];
    private boolean colony[][];
    private Ant ants[];
    private int maxFood;
    private int currentAnt;
    private int width;
    private int height;

    @Override
    public void copyTo(GPData gpData) {
        FieldData data = (FieldData) gpData;
        data.food = this.food;
        data.pheromones = this.pheromones;
        data.colony = this.colony;
        data.ants = this.ants;
        data.maxFood = this.maxFood;
        data.currentAnt = this.currentAnt;
        data.width = this.width;
        data.height = this.height;
    }
}
