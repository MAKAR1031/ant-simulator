package ru.makar.course.project.gp.node;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ru.makar.course.project.gp.data.Ant;
import ru.makar.course.project.gp.data.FieldData;

public class IfHasFoodNode extends GPNode {

    @Override
    public int expectedChildren() {
        return 2;
    }

    @Override
    public String toString() {
        return "IF-HAS-FOOD";
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
        children[ant.hasFood() ? 0 : 1].eval(state, thread, input, stack, individual, problem);
    }
}
