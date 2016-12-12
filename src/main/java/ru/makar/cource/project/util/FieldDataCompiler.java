package ru.makar.cource.project.util;

import ru.makar.cource.project.gp.data.Ant;
import ru.makar.cource.project.gp.data.Directions;
import ru.makar.cource.project.gp.data.FieldData;
import ru.makar.cource.project.gp.data.Position;
import ru.makar.cource.project.ui.TableFoodData;

import java.util.List;

public class FieldDataCompiler {
    public FieldData compile(int width, int height, int antCount, List<TableFoodData> tableFoodDataList) {
        boolean food[][] = new boolean[height][width];
        boolean pheromones[][] = new boolean[height][width];
        Position colony = new Position(0, 0, Directions.DOWN);
        Ant ants[] = new Ant[antCount];

        for (TableFoodData tableFoodData : tableFoodDataList) {
            int x = tableFoodData.getX();
            int y = tableFoodData.getY();
            food[x][y] = true;
        }

        int row = 0;
        int col = 0;
        for (int i = 0; i < antCount; i++) {
            Ant ant = new Ant();
            ant.setPosition(new Position(col, row, Directions.DOWN));
            ants[i] = ant;

            if (col + i >= width) {
                col = 0;
                row++;
            } else {
                col++;
            }
        }

        return FieldData.builder()
                .food(food)
                .pheromones(pheromones)
                .colony(colony)
                .ants(ants)
                .maxFood(tableFoodDataList.size())
                .currentAntIndex(0)
                .width(width)
                .height(height)
                .build();
    }
}
