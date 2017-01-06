package ru.makar.cource.project.gp.statistics;

import ec.EvolutionState;
import ec.gp.koza.KozaFitness;
import ec.simple.SimpleStatistics;
import ec.util.Parameter;
import ru.makar.cource.project.util.FieldDataStore;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.nio.channels.FileChannel.MapMode.READ_WRITE;
import static java.nio.file.StandardOpenOption.*;

public final class CustomStatistics extends SimpleStatistics {
    private static final Parameter GENERATIONS_PARAMETER = new Parameter("generations");
    private static final Parameter JOBS_PARAMETER = new Parameter("jobs");
    private static final Parameter DEFAULT_PARAMETER = null;

    private static int[] statistics;
    private static int maxFood;

    @Override
    public void setup(EvolutionState state, Parameter base) {
        super.setup(state, base);
        if (statistics == null) {
            statistics = new int[state.parameters.getInt(GENERATIONS_PARAMETER, DEFAULT_PARAMETER)];
            maxFood = FieldDataStore.getCurrentInstance().getData().getMaxFood();
        }
    }

    @Override
    public void finalStatistics(EvolutionState state, int result) {
        super.finalStatistics(state, result);
        if (((KozaFitness) best_of_run[0].fitness).hits == maxFood) {
            statistics[state.generation]++;
        }
        int currentJob = (int) state.job[0] + 1;
        if (currentJob == state.parameters.getInt(JOBS_PARAMETER, DEFAULT_PARAMETER)) {
            StringBuilder sb = new StringBuilder();
            for (int generation = 0; generation < statistics.length; generation++) {
                sb.append(generation).append(" ").append(statistics[generation]).append("\n");
            }
            byte[] bytes = sb.toString().getBytes();
            try {
                FileChannel channel = (FileChannel) Files
                        .newByteChannel(Paths.get("statistics.txt"), READ, WRITE, CREATE);
                MappedByteBuffer buffer = channel.map(READ_WRITE, 0, bytes.length);
                buffer.put(bytes);
            } catch (IOException e) {
                System.out.println("Произошла ошибка при записи в файл: " + e.getLocalizedMessage());
            }
        }
    }
}
