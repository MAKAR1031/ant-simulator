package ru.makar.course.project.gp.node;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ru.makar.course.project.gp.data.Ant;
import ru.makar.course.project.gp.data.Directions;
import ru.makar.course.project.gp.data.FieldData;
import ru.makar.course.project.gp.data.Position;

public class MoveRandomNode extends GPNode {

    private static final int MAX_TRY_COUNT = 3;

    @Override
    public int expectedChildren() {
        return 0;
    }

    @Override
    public String toString() {
        return "MOVE-RANDOM";
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
        int spin;
        int nextCol;
        int nextRow;
        Directions newDirection;
        int loopCount = 0;
        do {
            spin = state.random[thread].nextInt(4) + 1;
            newDirection = position.getDirection().turn(spin);
            nextCol = position.getCol() + 2 * newDirection.getColOffset();
            nextRow = position.getRow() + 2 * newDirection.getRowOffset();
            loopCount++;
            if (loopCount > MAX_TRY_COUNT) {
                return;
            }
        } while (!data.canMove(nextCol, nextRow, ant));
        position.setDirection(newDirection);
        ant.move(2);
    }
}
