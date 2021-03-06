package ru.makar.course.project.gp.data;

import ec.gp.GPData;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FieldData extends GPData {
    private boolean food[][];
    private boolean pheromones[][];
    private Position colony;
    private Ant ants[];
    private int maxFood;
    private int currentAntIndex;
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
        data.currentAntIndex = this.currentAntIndex;
        data.width = this.width;
        data.height = this.height;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("W = ").append(width);
        result.append("\n");
        result.append("H = ").append(height);
        result.append("\n");
        result.append("Food count = ").append(maxFood);
        result.append("\n");
        for (boolean[] row : food) {
            for (boolean cell : row) {
                result.append(cell ? "1 " : "0 ");
            }
            result.append("\n");
        }
        return result.toString();
    }

    public Ant getCurrentAnt() {
        return ants[currentAntIndex];
    }

    public boolean canMove(int col, int row, Ant currentAnt) {
        if (col < 0 || col >= width || row < 0 || row >= height) {
            return false;
        }

        for (Ant ant : ants) {
            if (!currentAnt.equals(ant)) {
                Position position = ant.getPosition();
                if (position.getCol() == col && position.getRow() == row) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean containsFood(int col, int row) {
        return checkPosition(col, row) && food[row][col];
    }

    public boolean containsFood(Position position) {
        return containsFood(position.getRow(), position.getCol());
    }

    boolean containsPheromone(int col, int row) {
        return checkPosition(col, row) && pheromones[row][col];
    }

    void pickupFood(Position position) {
        food[position.getCol()][position.getRow()] = false;
    }

    void dropPheromone(Position position) {
        pheromones[position.getRow()][position.getCol()] = true;
    }

    private boolean checkPosition(int col, int row) {
        return col >= 0 && col < width && row >= 0 && row < height;
    }
}
