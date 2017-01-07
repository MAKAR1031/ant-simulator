package ru.makar.course.project.gp.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class Ant {
    private Position position;
    private int count;
    private int feedFood;

    public boolean hasFood() {
        return feedFood > 0;
    }

    public void pickupFood(FieldData data) {
        data.pickupFood(position);
        feedFood++;
    }

    public void dropPheromone(FieldData data) {
        data.dropPheromone(position);
    }

    public void move(int steps) {
        Directions direction = position.getDirection();
        for (int i = 0; i < steps; i++) {
            position.setCol(position.getCol() + direction.getColOffset());
            position.setRow(position.getRow() + direction.getRowOffset());
        }
        count++;
    }

    public boolean detectPheromone(FieldData data, int col, int row) {
        for (int dRow = row - 1; dRow <= row + 1; dRow++) {
            for (int dCol = col - 1; dCol <= col + 1; dCol++) {
                if (data.containsPheromone(dCol, dRow)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean detectFood(FieldData data, Position position) {
        int row = position.getRow();
        int col = position.getCol();
        for (int dRow = row - 1; dRow <= row + 1; dRow++) {
            for (int dCol = col - 1; dCol <= col + 1; dCol++) {
                if (data.containsFood(dCol, dRow)) {
                    return true;
                }
            }
        }
        return false;
    }
}
