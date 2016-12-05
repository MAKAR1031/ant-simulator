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

public class MoveAdjFoodElseNode extends GPNode {

    @Override
    public int expectedChildren() {
        return 1;
    }

    @Override
    public String toString() {
        return "MOVE-ADJ-FOOD-ELSE";
    }

    @Override
    public void eval(EvolutionState state, int thread, GPData input, ADFStack stack, GPIndividual individual, Problem problem) {
        FieldData data = (FieldData) input;
        Ant ant = data.getCurrentAnt();
        Directions direction = ant.getPosition().getDirection();

        if (tryMoveAndEat(data, ant, direction)) return;
        if (tryMoveAndEat(data, ant, direction.turnRight())) return;
        if (tryMoveAndEat(data, ant, direction.turnLeft())) return;
        if (tryMoveAndEat(data, ant, direction.turnAround())) return;

        children[0].eval(state, thread, input, stack, individual, problem);
    }

    private boolean tryMoveAndEat(FieldData data, Ant ant, Directions direction) {
        Position position = ant.getPosition();
        int nextCol = position.getCol() + direction.getColOffset();
        int nextRow = position.getRow() + direction.getRowOffset();
        if (data.canMove(nextCol, nextRow, ant) && data.containsFood(nextCol, nextRow)) {
            position.setDirection(direction);
            ant.move(1);
            ant.pickupFood(data);
            return true;
        }
        return false;
    }
}
