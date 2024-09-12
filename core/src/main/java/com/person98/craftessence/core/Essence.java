package com.person98.craftessence.core;

import com.person98.craftessence.util.annotations.Configurable;
import com.person98.craftessence.util.config.ConfigHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Essence represents the core interface for a configurable system.
 * It provides methods for loading, retrieving, and managing configurations,
 * as well as lifecycle event callbacks.
 */
public interface Essence {

    /**
     * The `configMap` is a `Map` object that stores the configurations of different classes.
     * It is used in the `Essence` interface to load and retrieve configurations for various classes.
     * The keys of the map are of type `Class<?>`, which represent the class for which the configuration is stored.
     * The values of the map are of type `Object`, which represent the configuration instance associated with the class.
     *
     * <p>Usage:</p>
     * <pre>{@code
     * Map<Class<?>, Object> configMap = new HashMap<>();
     * }</pre>
     *
     * @see Essence
     */
    Map<Class<?>, Object> configMap = new HashMap<>();

    /**
     * Loads a configuration object for a specific class if it is annotated with @Configurable.
     *
     * @param <T>   the type of the configuration class
     * @param clazz the class of the configuration object
     */
    default <T> void loadConfig(Class<T> clazz) {
        Configurable configurable = clazz.getAnnotation(Configurable.class);
        if (configurable != null) {
            ConfigHandler configHandler = new ConfigHandler(this.getClass().getSimpleName());
            T configInstance = configHandler.loadConfig(clazz);
            configMap.put(clazz, configInstance);
        }
    }

    /**
     * Retrieves the configuration instance for the specified class.
     *
     * @param clazz the class representing the configuration
     * @param <T>   the type of the configuration instance
     * @return the configuration instance for the specified class, or null if it doesn't exist
     */
    @SuppressWarnings("unchecked")
    default <T> T getConfig(Class<T> clazz) {
        return (T) configMap.get(clazz);
    }

    /**
     * Performs actions before enabling the configurable system.
     */
    void onPreEnable();

    /**
     * Method onEnable is a callback method that is called when the system is enabled.
     * It is usually used to initialize and start components of the system.
     */
    void onEnable();

    /**
     * This method is called before the Essence is disabled. It can be used to perform any necessary cleanup or preparation tasks before the Essence is disabled.
     */
    void onPreDisable();

    /**
     * Called when the plugin is being disabled.
     */
    void onDisable();

    /**
     * Callback method that is called when the configuration is reloaded.
     * This method is invoked after the configuration file is loaded and the new configuration is available.
     */
    void onReload();
}
