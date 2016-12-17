package ru.makar.cource.project;

import ec.Evolve;

public class ExperimentLauncher {

    private static final String PROPERTY_FILE = "src/main/resources/params.properties";

    public static void launch() {
        Evolve.main(new String[]{Evolve.A_FILE, PROPERTY_FILE});
    }
}
