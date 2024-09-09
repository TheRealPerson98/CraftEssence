package com.person98.craftessence.loader;

import java.util.HashMap;
import java.util.Map;

public class ClassHolder {

    // Map to store loaded classes by class name
    private final Map<String, Class<?>> classMap = new HashMap<>();

    // Add a class to the classMap
    public void addClass(String className, Class<?> clazz) {
        classMap.put(className, clazz);
    }

    // Get a class by its name
    public Class<?> getClass(String className) {
        return classMap.get(className);
    }

    // Check if a class is already loaded
    public boolean isClassLoaded(String className) {
        return classMap.containsKey(className);
    }
}
