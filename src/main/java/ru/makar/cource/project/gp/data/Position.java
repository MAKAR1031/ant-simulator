package ru.makar.cource.project.gp.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Position {
    private int col;
    private int row;
    private Directions direction;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;

        Position position = (Position) o;

        if (col != position.col) return false;
        return row == position.row;
    }

    @Override
    public int hashCode() {
        int result = col;
        result = 31 * result + row;
        return result;
    }
}
