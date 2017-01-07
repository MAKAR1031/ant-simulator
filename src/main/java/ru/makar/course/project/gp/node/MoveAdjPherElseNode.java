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

public class MoveAdjPherElseNode extends GPNode {

    @Override
    public int expectedChildren() {
        return 1;
    }

    @Override
    public String toString() {
        return "MOVE-ADJ-PHER-ELSE";
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
        Directions direction = ant.getPosition().getDirection();

        if (tryMoveToPheromone(data, ant, direction)) return;
        if (tryMoveToPheromone(data, ant, direction.turnRight())) return;
        if (tryMoveToPheromone(data, ant, direction.turnLeft())) return;
        if (tryMoveToPheromone(data, ant, direction.turnAround())) return;

        children[0].eval(state, thread, input, stack, individual, problem);
    }

    private boolean tryMoveToPheromone(FieldData data, Ant ant, Directions direction) {
        Position position = ant.getPosition();
        int nextCol = position.getCol() + direction.getColOffset();
        int nextRow = position.getRow() + direction.getRowOffset();
        if (data.canMove(nextCol, nextRow, ant) && ant.detectPheromone(data, nextCol, nextRow)) {
            position.setDirection(direction);
            ant.move(1);
            return true;
        }
        return false;
    }
}
