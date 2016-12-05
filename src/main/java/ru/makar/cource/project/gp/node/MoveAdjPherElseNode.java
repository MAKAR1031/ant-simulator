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
    public void eval(EvolutionState state, int thread, GPData input, ADFStack stack, GPIndividual individual, Problem problem) {
        FieldData data = (FieldData) input;
        Ant ant = data.getAnts()[data.getCurrentAnt()];
        Directions direction = ant.getPosition().getDirection();

        if (moveToPheromone(data, ant, direction)) return;
        if (moveToPheromone(data, ant, direction.turnRight())) return;
        if (moveToPheromone(data, ant, direction.turnLeft())) return;
        if (moveToPheromone(data, ant, direction.turnAround())) return;

        children[0].eval(state, thread, input, stack, individual, problem);
    }

    private boolean moveToPheromone(FieldData data, Ant ant, Directions direction) {
        Position position = ant.getPosition();
        int nextX = position.getX() + direction.getXOffset();
        int nextY = position.getY() + direction.getYOffset();
        if (nextX > 0 && nextX < data.getFood().length && data.getPheromones()[nextX][nextY]) {
            position.setX(nextX);
            position.setY(nextY);
            ant.setCount(ant.getCount() + 1);
            position.setDirection(direction);
            return true;
        }
        return false;
    }
}
