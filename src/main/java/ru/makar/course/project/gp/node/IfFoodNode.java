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

public class IfFoodNode extends GPNode {

    @Override
    public int expectedChildren() {
        return 2;
    }

    @Override
    public String toString() {
        return "IF-FOOD";
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
        int executeChild = ant.detectFood(data, position) ? 0 : 1;
        children[executeChild].eval(state, thread, input, stack, individual, problem);
    }
}
