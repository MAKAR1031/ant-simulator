package ru.makar.cource.project;

import ec.Evolve;

public class ExperimentLauncher {
    public static void launch() {
        Evolve.main(new String[]{Evolve.A_FILE, "src/main/resources/params.properties"});
    }
}
