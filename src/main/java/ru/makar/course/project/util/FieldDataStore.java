package ru.makar.course.project.util;

import com.rits.cloning.Cloner;
import lombok.Getter;
import lombok.Setter;
import ru.makar.course.project.gp.data.FieldData;

public final class FieldDataStore {
    private static final FieldDataStore INSTANCE = new FieldDataStore();

    private static Cloner cloner;

    private FieldData data;

    @Getter
    @Setter
    private int omega1;

    @Getter
    @Setter
    private int omega2;

    public static FieldDataStore getInstance() {
        return INSTANCE;
    }

    private FieldDataStore() {
        cloner = new Cloner();
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
