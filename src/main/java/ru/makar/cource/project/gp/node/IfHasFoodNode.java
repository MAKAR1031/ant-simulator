package ru.makar.cource.project.gp.node;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;

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
    public void eval(EvolutionState state, int thread, GPData input, ADFStack stack, GPIndividual individual, Problem problem) {

    }
}
