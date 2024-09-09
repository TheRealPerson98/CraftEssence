package com.person98.craftessence.core;

import java.util.HashMap;
import java.util.Map;

public class Instances {

    // Map to store the class type and its instance
    private static final Map<Class<? extends Essence>, Essence> instances = new HashMap<>();

    // Register a class type and bind it to its instance
    public static <T extends Essence> void register(Class<T> clazz, T instance) {
        if (clazz == null || instance == null) {
            throw new IllegalArgumentException("Class or instance cannot be null.");
        }

        if (!instances.containsKey(clazz)) {
            instances.put(clazz, instance);
        } else {
            throw new IllegalStateException("Instance for class " + clazz.getName() + " is already registered.");
        }
    }

    // Retrieve the instance of a registered class
    public static <T extends Essence> T get(Class<T> clazz) {
        Essence instance = instances.get(clazz);

        if (instance == null) {
            throw new IllegalStateException("No instance registered for class: " + clazz.getName());
        }

        // Suppress the unchecked cast warning, as we are sure it's the correct type
        @SuppressWarnings("unchecked")
        T castedInstance = (T) instance;

        return castedInstance;
    }

    // Check if a class has been registered
    public static <T extends Essence> boolean isRegistered(Class<T> clazz) {
        return instances.containsKey(clazz);
    }
}
