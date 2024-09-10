package com.person98.craftessence.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Instances class provides a way to register and retrieve instances of classes that extend the Essence interface.
 * It stores a mapping between class types and their instances using a Map data structure.
 * An instance can be registered and retrieved using the register() and get() methods respectively.
 * The class also provides a method to check if a class type has been registered or not using the isRegistered() method.
 */
public class Instances {

    /**
     * Map to store the class type and its instance
     * @since 1.0.0
     */
    // Map to store the class type and its instance
    private static final Map<Class<? extends Essence>, Essence> instances = new HashMap<>();

    /**
     * Register a class type and bind it to its instance.
     *
     * @param clazz    The class type to be registered.
     * @param instance The instance of the class type to be bound.
     * @throws IllegalArgumentException      If either the clazz or instance is null.
     * @throws IllegalStateException         If an instance for the class is already registered.
     */
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

    /**
     * Retrieves the instance of a registered class.
     *
     * @param <T>   the type of the class that extends Essence
     * @param clazz the class to retrieve the instance for
     * @return the instance of the specified class
     * @throws IllegalStateException if no instance is registered for the specified class
     */
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

    /**
     * Check if a class has been registered.
     *
     * @param <T> the type parameter for the class that extends Essence
     * @param clazz the class to check if it has been registered
     * @return true if the class has been registered, false otherwise
     */
    // Check if a class has been registered
    public static <T extends Essence> boolean isRegistered(Class<T> clazz) {
        return instances.containsKey(clazz);
    }
}
