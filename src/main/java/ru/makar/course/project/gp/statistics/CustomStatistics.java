package ru.makar.course.project.gp.statistics;

import ec.EvolutionState;
import ec.gp.koza.KozaFitness;
import ec.simple.SimpleStatistics;
import ec.util.Parameter;
import org.apache.commons.lang3.tuple.MutablePair;
import ru.makar.course.project.util.FieldDataStore;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static java.nio.channels.FileChannel.MapMode.READ_WRITE;
import static java.nio.file.StandardOpenOption.*;

public final class CustomStatistics extends SimpleStatistics {
    private static final Parameter JOBS_PARAMETER = new Parameter("jobs");
    private static final Parameter DEFAULT_PARAMETER = null;
    private static final String FILE_NAME = "statistics.txt";

    private static Map<Integer, MutablePair<Integer, Integer>> statistics;
    private static int maxFood;

    @Override
    public void setup(EvolutionState state, Parameter base) {
        super.setup(state, base);
        if (statistics == null) {
            statistics = new HashMap<>();
            maxFood = FieldDataStore.getInstance().getData().getMaxFood();
        }
    }

    @Override
    public void finalStatistics(EvolutionState state, int result) {
        super.finalStatistics(state, result);
        KozaFitness fitness = (KozaFitness) best_of_run[0].fitness;
        int hits = fitness.hits;
        boolean isCollectAllFood = hits == maxFood;
        if (statistics.containsKey(state.generation)) {
            MutablePair<Integer, Integer> pair = statistics.get(state.generation);
            if (hits > pair.getLeft()) {
                pair.setLeft(hits);
            }
            if (isCollectAllFood) {
                pair.setRight(pair.getRight() + 1);
            }
        } else {
            statistics.put(state.generation, new MutablePair<>(hits, isCollectAllFood ? 1 : 0));
        }

        int currentJob = (int) state.job[0] + 1;
        if (currentJob == state.parameters.getInt(JOBS_PARAMETER, DEFAULT_PARAMETER)) {
            writeStatisticsToFile();
        }
    }

    private void writeStatisticsToFile() {
        StringBuilder sb = new StringBuilder();
        sb.append("GEN | MAX_FOOD | FIND_OPTIMAL").append("\n");
        for (Integer key : statistics.keySet()) {
            MutablePair<Integer, Integer> pair = statistics.get(key);
            sb.append(key).append(" ").append(pair.getLeft()).append(" ").append(pair.getRight()).append("\n");
        }
        byte[] bytes = sb.toString().getBytes();
        try (FileChannel channel = (FileChannel) Files.newByteChannel(Paths.get(FILE_NAME), READ, WRITE, CREATE)) {
            MappedByteBuffer buffer = channel.map(READ_WRITE, 0, bytes.length);
            buffer.put(bytes);
        } catch (IOException e) {
            System.out.println("Произошла ошибка при записи в файл: " + e.getLocalizedMessage());
        }
    }
}
