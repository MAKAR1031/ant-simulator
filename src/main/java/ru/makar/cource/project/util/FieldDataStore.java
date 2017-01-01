package ru.makar.cource.project.util;

import com.rits.cloning.Cloner;
import lombok.Getter;
import lombok.Setter;
import ru.makar.cource.project.gp.data.FieldData;

public class FieldDataStore {
    private static FieldDataStore currentInstance;
    private static Cloner cloner;

    private FieldData data;

    @Getter
    @Setter
    private int omega1;

    @Getter
    @Setter
    private int omega2;

    private FieldDataStore() {
        cloner = new Cloner();
    }

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
        return cloner.deepClone(data);
    }

    public void setData(FieldData data) {
        this.data = cloner.deepClone(data);
    }
}
