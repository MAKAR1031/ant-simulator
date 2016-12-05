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
        Ant ant = data.getAnts()[data.getCurrentAnt()];
        Directions direction = ant.getPosition().getDirection();

        if (moveAndEat(data, ant, direction)) return;
        if (moveAndEat(data, ant, direction.turnRight())) return;
        if (moveAndEat(data, ant, direction.turnLeft())) return;
        if (moveAndEat(data, ant, direction.turnAround())) return;

        children[0].eval(state, thread, input, stack, individual, problem);
    }

    private boolean moveAndEat(FieldData data, Ant ant, Directions direction) {
        Position position = ant.getPosition();
        int nextX = position.getX() + direction.getXOffset();
        int nextY = position.getY() + direction.getYOffset();
        if (nextX > 0 && nextX < data.getFood().length && data.getFood()[nextX][nextY]) {
            position.setX(nextX);
            position.setY(nextY);
            position.setDirection(direction);
            data.getFood()[nextX][nextY] = false;
            ant.setCount(ant.getCount() + 1);
            ant.setFeedFood(ant.getFeedFood() + 1);
            return true;
        }
        return false;
    }
}
