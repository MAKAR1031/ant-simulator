package ru.makar.cource.project.gp.node;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ru.makar.cource.project.gp.data.Ant;
import ru.makar.cource.project.gp.data.FieldData;
import ru.makar.cource.project.gp.data.Position;

import static ru.makar.cource.project.gp.data.Directions.*;

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
    public void eval(EvolutionState state, int thread, GPData input, ADFStack stack, GPIndividual individual, Problem problem) {
        FieldData data = (FieldData) input;
        Ant ant = data.getAnts()[data.getCurrentAnt()];
        Position position = ant.getPosition();
        Position colony = data.getColony();
        int dx = position.getX() - colony.getX();
        int dy = position.getY() - colony.getY();
        boolean needMove = dx != 0 || dy != 0;
        if (dx > dy && dx != 0) {
            position.setDirection(dx > 0 ? LEFT : RIGHT);
        } else if (dy != 0) {
            position.setDirection(dy > 0 ? UP : DOWN);
        }
        if (needMove) {
            position.setX(position.getX() + position.getDirection().getXOffset());
            position.setY(position.getY() + position.getDirection().getYOffset());
        }
    }
}
