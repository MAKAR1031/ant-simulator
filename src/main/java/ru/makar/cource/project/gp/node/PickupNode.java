package ru.makar.cource.project.gp.node;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ru.makar.cource.project.gp.data.Ant;
import ru.makar.cource.project.gp.data.FieldData;

public class PickupNode extends GPNode {

    @Override
    public int expectedChildren() {
        return 0;
    }

    @Override
    public String toString() {
        return "PICK-UP";
    }

    @Override
    public void eval(EvolutionState state, int thread, GPData input, ADFStack stack, GPIndividual individual, Problem problem) {
        FieldData data = (FieldData) input;
        Ant ant = data.getCurrentAnt();
        if (data.containsFood(ant.getPosition())) {
            ant.pickupFood(data);
        }
    }
}
