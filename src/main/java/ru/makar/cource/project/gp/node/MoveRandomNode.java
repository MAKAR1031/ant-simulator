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
        Ant ant = data.getAnts()[data.getCurrentAnt()];
        int spin = state.random[thread].nextInt(4) + 1;
        Directions newDirection = Directions.values()[(ant.getPosition().getDirection().ordinal() + spin) % Directions.values().length];
        ant.getPosition().setDirection(newDirection);
        Position position = ant.getPosition();
        int newX = position.getX() + newDirection.getXOffset();
        if (newX > data.getWidth()) newX = data.getWidth();
        int newY = position.getY() + newDirection.getYOffset();
        if (newY > data.getHeight()) newY = data.getHeight();
        position.setX(newX);
        position.setY(newY);
    }
}
