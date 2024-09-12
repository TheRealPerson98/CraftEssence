package com.person98.craftessence.loader;

import com.person98.craftessence.core.Essence;
import com.person98.craftessence.util.annotations.EssenceInfo;
import com.person98.craftessence.util.logging.EssenceLogger;

import java.util.*;

/**
 * The LoadOrder class represents a utility class for loading essences.
 * An essence represents the core interface for a configurable system
 * and provides methods for loading, retrieving, and managing configurations,
 * as well as lifecycle event callbacks.
 */
public class LoadOrder {

    private final List<Essence> loadedEssences;

    public LoadOrder(List<Essence> loadedEssences) {
        this.loadedEssences = loadedEssences;
    }

    /**
     * Loads the essences in order based on their internal dependencies.
     * Disables loading if a circular dependency is found.
     */
    // Method to load essences based on internal dependencies
    public void loadInOrder() {
        Map<String, Essence> essenceMap = new HashMap<>();
        Map<String, List<String>> dependencyGraph = new HashMap<>();

        // Populate the maps
        for (Essence essence : loadedEssences) {
            EssenceInfo info = essence.getClass().getAnnotation(EssenceInfo.class);
            if (info != null) {
                String name = info.name();
                essenceMap.put(name, essence);
                dependencyGraph.put(name, Arrays.asList(info.internalDependencies()));
            }
        }

        // Check for circular dependencies
        if (hasCircularDependencies(dependencyGraph)) {
            EssenceLogger.Info("Circular dependency detected! Disabling all essences.");
            return; // Disable loading if circular dependency is found
        }

        List<String> loadOrder = getLoadOrder(dependencyGraph);

        // Load essences in the correct order
        for (String essenceName : loadOrder) {
            Essence essence = essenceMap.get(essenceName);
            if (essence != null) {
                EssenceLogger.Info("Loading essence: " + essenceName);
                try {
                    essence.onPreEnable();
                    essence.onEnable();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Gets the correct loading order based on the internal dependencies of the essences.
     *
     * @param dependencyGraph a map representing the internal dependencies of the essences
     * @return a list of essences in the correct loading order, or an empty list if a circular dependency is detected
     */
    // Topological sorting to get the correct loading order based on internal dependencies
    private List<String> getLoadOrder(Map<String, List<String>> dependencyGraph) {
        List<String> sortedOrder = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        Set<String> visiting = new HashSet<>();

        for (String essence : dependencyGraph.keySet()) {
            if (!visited.contains(essence)) {
                if (dfs(essence, dependencyGraph, visited, visiting, sortedOrder)) {
                    return Collections.emptyList(); // Return empty list if a cycle is detected
                }
            }
        }
        return sortedOrder;
    }

    /**
     * Performs a depth-first search (DFS) on a dependency graph to determine the load order of essences.
     *
     * @param current         the current essence being visited
     * @param dependencyGraph a map representing the dependency graph, where each key is an essence and the corresponding value is a list of its dependencies
     * @param visited         a set of visited essences
     * @param visiting        a set of essences currently being visited
     * @param sortedOrder     the list to store the sorted load order of essences
     * @return true if a circular dependency is detected, false otherwise
     */
    private boolean dfs(String current, Map<String, List<String>> dependencyGraph, Set<String> visited, Set<String> visiting, List<String> sortedOrder) {
        visiting.add(current);
        for (String dependency : dependencyGraph.getOrDefault(current, Collections.emptyList())) {
            if (!visited.contains(dependency)) {
                if (visiting.contains(dependency)) {
                    // Circular dependency detected
                    return true;
                }
                if (dfs(dependency, dependencyGraph, visited, visiting, sortedOrder)) {
                    return true;
                }
            }
        }
        visiting.remove(current);
        visited.add(current);
        sortedOrder.add(current); // Add to the load order after processing dependencies
        return false;
    }

    /**
     * Checks whether the given dependency graph has circular dependencies.
     *
     * @param dependencyGraph the dependency graph represented as a map
     *                        where the keys are the essences and the values are lists
     *                        of their dependencies
     * @return true if the dependency graph has circular dependencies, false otherwise
     */
    private boolean hasCircularDependencies(Map<String, List<String>> dependencyGraph) {
        Set<String> visited = new HashSet<>();
        Set<String> visiting = new HashSet<>();

        for (String essence : dependencyGraph.keySet()) {
            if (detectCycle(essence, dependencyGraph, visited, visiting)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Detects cycles in a dependency graph starting from a given node.
     *
     * @param current         the current node to start the detection from
     * @param dependencyGraph the dependency graph represented as a map of nodes and their dependencies
     * @param visited         a set of visited nodes
     * @param visiting        a set of nodes currently being visited
     * @return true if a cycle is detected, false otherwise
     */
    private boolean detectCycle(String current, Map<String, List<String>> dependencyGraph, Set<String> visited, Set<String> visiting) {
        if (visiting.contains(current)) {
            return true; // Circular dependency detected
        }
        if (visited.contains(current)) {
            return false; // Already processed this node
        }
        visiting.add(current);
        for (String dependency : dependencyGraph.getOrDefault(current, Collections.emptyList())) {
            if (detectCycle(dependency, dependencyGraph, visited, visiting)) {
                return true;
            }
        }
        visiting.remove(current);
        visited.add(current);
        return false;
    }
}
