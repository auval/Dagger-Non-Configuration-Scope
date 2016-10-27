package com.github.partition.nonconfscope;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.ArrayMap;
import com.github.partition.nonconfscope.dagger.NonConfigurationComponent;

import java.util.UUID;

public class NonConfigurationComponentSaver {
    private static class SingletonHolder {
        private static final NonConfigurationComponentSaver INSTANCE = new NonConfigurationComponentSaver();
    }

    public static NonConfigurationComponentSaver getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private ArrayMap<String, NonConfigurationComponent> container;

    private NonConfigurationComponentSaver() {
        container = new ArrayMap<>();
    }

    public String push(@NonNull NonConfigurationComponent component) {
        String id = generateId();
        container.put(id, component);
        return id;
    }

    @Nullable
    public NonConfigurationComponent pull(String id) {
        return container.remove(id);
    }

    private String generateId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
