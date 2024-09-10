package com.person98.craftessence.loader;

import java.util.HashMap;
import java.util.Map;

/**
 * A class that holds a mapping of class names to their corresponding Class objects.
 */
public class ClassHolder {

    /**
     * A private final field that holds a mapping of class names to their corresponding Class objects.
     * This map is an instance of the HashMap class.
     *
     * The key in the map is a String representing the class name, and the value is a Class object representing the corresponding class.
     */
    private final Map<String, Class<?>> classMap = new HashMap<>();

    /**
     * Adds a mapping of a class name to its corresponding Class object.
     *
     * @param className The name of the class.
     * @param clazz The Class object representing the class.
     */
    public void addClass(String className, Class<?> clazz) {
        classMap.put(className, clazz);
    }

    /**
     * Retrieves the Class object associated with the given class name.
     *
     * @param className the name of the class to retrieve
     * @return the Class object associated with the given class name, or null if not found
     */
    public Class<?> getClass(String className) {
        return classMap.get(className);
    }

    /**
     * Checks if a class is loaded.
     *
     * @param className the name of the class to check
     * @return true if the class is loaded, false otherwise
     */
    public boolean isClassLoaded(String className) {
        return classMap.containsKey(className);
    }
}
