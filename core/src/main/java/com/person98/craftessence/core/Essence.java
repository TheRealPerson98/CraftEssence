package com.person98.craftessence.core;

import com.person98.craftessence.util.annotations.Configurable;
import com.person98.craftessence.util.config.ConfigHandler;

import java.util.HashMap;
import java.util.Map;

public interface Essence {

    // Store loaded configuration objects
    Map<Class<?>, Object> configMap = new HashMap<>();

    // Method to load a configuration class
    default <T> void loadConfig(Class<T> clazz) {
        Configurable configurable = clazz.getAnnotation(Configurable.class);
        if (configurable != null) {
            ConfigHandler configHandler = new ConfigHandler(this.getClass().getSimpleName());
            T configInstance = configHandler.loadConfig(clazz);
            configMap.put(clazz, configInstance);
        }
    }

    // Method to retrieve a loaded configuration class
    @SuppressWarnings("unchecked")
    default <T> T getConfig(Class<T> clazz) {
        return (T) configMap.get(clazz);
    }

    // Other methods for the lifecycle of Essence
    void onPreEnable();

    void onEnable();

    void onPreDisable();

    void onDisable();

    void onReload();
}
