package ru.makar.course.project.gp.node;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ru.makar.course.project.gp.data.Ant;
import ru.makar.course.project.gp.data.FieldData;
import ru.makar.course.project.gp.data.Position;

import static ru.makar.course.project.gp.data.Directions.*;

public class MoveNestNode extends GPNode {

    @Override
    public int expectedChildren() {
        return 0;
    }

    @Override
    public String toString() {
        return "MOVE-NEST";
    }

    @Override
    public void eval(EvolutionState state,
                     int thread,
                     GPData input,
                     ADFStack stack,
                     GPIndividual individual,
                     Problem problem) {
        FieldData data = (FieldData) input;
        Ant ant = data.getCurrentAnt();
        Position position = ant.getPosition();
        Position colony = data.getColony();
        int dCol = position.getCol() - colony.getCol();
        int dRow = position.getRow() - colony.getRow();
        boolean needMove = dCol != 0 || dRow != 0;
        if (Math.abs(dCol) > Math.abs(dRow) && dCol != 0) {
            position.setDirection(dCol > 0 ? LEFT : RIGHT);
        } else if (dRow != 0) {
            position.setDirection(dRow > 0 ? UP : DOWN);
        }
        if (needMove) {
            ant.move(1);
        }
    }
}
