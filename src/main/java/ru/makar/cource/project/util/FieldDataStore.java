package ru.makar.cource.project.util;

import lombok.Getter;
import lombok.Setter;
import ru.makar.cource.project.gp.data.FieldData;

public class FieldDataStore {
    private static FieldDataStore currentInstance;

    private FieldData data;

    @Getter
    @Setter
    private int omega1;

    @Getter
    @Setter
    private int omega2;

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
