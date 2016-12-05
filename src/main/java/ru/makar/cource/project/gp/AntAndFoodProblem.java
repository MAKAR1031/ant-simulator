package ru.makar.cource.project.gp;

import ec.EvolutionState;
import ec.Individual;
import ec.gp.GPIndividual;
import ec.gp.GPProblem;
import ec.gp.koza.KozaFitness;
import ec.simple.SimpleProblemForm;
import ru.makar.cource.project.gp.data.Ant;
import ru.makar.cource.project.gp.data.FieldData;

public class AntAndFoodProblem extends GPProblem implements SimpleProblemForm {

    private static final double omega1 = 100;
    private static final double omega2 = 10;

    @Override
    public void evaluate(EvolutionState state, Individual ind, int subpopulation, int threadnum) {
        if (ind.evaluated) {
            return;
        }

        GPIndividual individual = (GPIndividual) ind;
        FieldData data = (FieldData) input;
        for (int i = 0; i < data.getAnts().length; i++) {
            data.setCurrentAntIndex(i);
            individual.trees[0].child.eval(state, threadnum, input, stack, individual, this);
        }

        int maxFood = data.getMaxFood();
        int food = 0;
        int count = 0;
        for (Ant ant : data.getAnts()) {
            food += ant.getFeedFood();
            count += ant.getCount();
        }
        double value = omega1 * (maxFood - food) + omega2 * count;

        ind.evaluated = true;
        KozaFitness fitness = (KozaFitness) ind.fitness;
        fitness.setStandardizedFitness(state, value);
    }
}
