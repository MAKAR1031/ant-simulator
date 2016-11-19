package ru.makar.cource.project.gp.data;

import lombok.Data;

@Data
public class Ant {
    private Position position;
    private int count;
    private int feedFood;

    public boolean haveFood() {
        return feedFood > 0;
    }
}
