package ru.makar.cource.project.gp;

import ec.EvolutionState;
import ec.Individual;
import ec.gp.GPIndividual;
import ec.gp.GPProblem;
import ec.gp.koza.KozaFitness;
import ec.simple.SimpleProblemForm;
import ec.util.Parameter;
import ru.makar.cource.project.gp.data.Ant;
import ru.makar.cource.project.gp.data.FieldData;
import ru.makar.cource.project.util.FieldDataStore;

public class AntAndFoodProblem extends GPProblem implements SimpleProblemForm {

    private double omega1;
    private double omega2;

    @Override
    public void setup(EvolutionState state, Parameter base) {
        super.setup(state, base);
        FieldDataStore dataStore = FieldDataStore.getCurrentInstance();
        if (!dataStore.containsData()) {
            state.output.fatal("Входные данные отсуствуют");
        }
        input = dataStore.getData();
        omega1 = dataStore.getOmega1();
        omega2 = dataStore.getOmega2();
    }

    @Override
    public void evaluate(EvolutionState state, Individual ind, int subPopulation, int threadNum) {
        if (ind.evaluated) {
            return;
        }

        GPIndividual individual = (GPIndividual) ind;
        FieldData data = (FieldData) input;
        for (int j = 0; j < 100; j++) {
            for (int i = 0; i < data.getAnts().length; i++) {
                data.setCurrentAntIndex(i);
                individual.trees[0].child.eval(state, threadNum, input, stack, individual, this);
            }
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
