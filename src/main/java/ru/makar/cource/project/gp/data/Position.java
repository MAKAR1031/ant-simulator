package ru.makar.cource.project.gp.data;

import lombok.Data;

@Data
public class Position {
    private int x;
    private int y;
    private Directions direction;
}
