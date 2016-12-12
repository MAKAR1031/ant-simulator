package ru.makar.cource.project.util;

import ru.makar.cource.project.gp.data.FieldData;

public class FieldDataStore {
    private static FieldDataStore currentInstance;

    private FieldData data;

    private FieldDataStore() { }

    public static FieldDataStore getCurrentInstance() {
        if (currentInstance == null) {
            currentInstance = new FieldDataStore();
        }
        return currentInstance;
    }

    public boolean containsData() {
        return data != null;
    }

    public FieldData getData() {
        return (FieldData) data.clone();
    }

    public void setData(FieldData data) {
        this.data = (FieldData) data.clone();
    }
}
