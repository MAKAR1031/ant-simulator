package ru.makar.cource.project.util;

import ru.makar.cource.project.gp.data.FieldData;
import ru.makar.cource.project.ui.FoodCord;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class FileDataReader {

    private FieldDataCompiler fieldDataCompiler;

    public FileDataReader() {
        fieldDataCompiler = new FieldDataCompiler();
    }

    public FieldData readData(File dataFile) {
        if (dataFile == null || !dataFile.exists()) return null;
        try (Scanner scanner = new Scanner(dataFile)) {
            int width = scanner.nextInt();
            int height = scanner.nextInt();
            int foodCount = scanner.nextInt();
            int antCount = scanner.nextInt();
            List<FoodCord> foodCoors = new LinkedList<>();
            for (int i = 0; i < foodCount; i++) {
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                FoodCord data = new FoodCord(x, y);
                foodCoors.add(data);
            }
            return fieldDataCompiler.compile(width, height, antCount, foodCoors);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
