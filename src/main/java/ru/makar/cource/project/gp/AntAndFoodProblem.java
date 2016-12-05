package ru.makar.cource.project.gp;

import ec.EvolutionState;
import ec.Individual;
import ec.gp.GPProblem;
import ec.gp.koza.KozaFitness;
import ec.simple.SimpleProblemForm;

public class AntAndFoodProblem extends GPProblem implements SimpleProblemForm {

    @Override
    public void evaluate(EvolutionState state, Individual ind, int subpopulation, int threadnum) {
        if (ind.evaluated) {
            return;
        }

        ind.evaluated = true;
        KozaFitness fitness = (KozaFitness) ind.fitness;
        fitness.setStandardizedFitness(state, 0);
    }
}
