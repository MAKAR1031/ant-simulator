package ru.makar.cource.project.gp.node;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ru.makar.cource.project.gp.data.Ant;
import ru.makar.cource.project.gp.data.Directions;
import ru.makar.cource.project.gp.data.FieldData;
import ru.makar.cource.project.gp.data.Position;

public class MoveRandomNode extends GPNode {

    @Override
    public int expectedChildren() {
        return 0;
    }

    @Override
    public String toString() {
        return "MOVE-RANDOM";
    }

    @Override
    public void eval(EvolutionState state, int thread, GPData input, ADFStack stack, GPIndividual individual, Problem problem) {
        FieldData data = (FieldData) input;
        Ant ant = data.getCurrentAnt();
        int spin = state.random[thread].nextInt(4) + 1;
        Position position = ant.getPosition();
        Directions newDirection = position.getDirection().turn(spin);
        int nextCol = position.getCol() + 2 * newDirection.getColOffset();
        int nextRow = position.getRow() + 2 * newDirection.getRowOffset();
        if (data.canMove(nextCol, nextRow, ant)) {
            position.setDirection(newDirection);
            ant.move(2);
        } else {
            this.eval(state, thread, input, stack, individual, problem);
        }
    }
}
