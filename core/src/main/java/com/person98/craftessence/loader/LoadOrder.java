package com.person98.craftessence.loader;

import com.person98.craftessence.core.Essence;
import com.person98.craftessence.util.annotations.EssenceInfo;
import com.person98.craftessence.util.logging.EssenceLogger;

import java.util.*;

public class LoadOrder {

    private final List<Essence> loadedEssences;

    public LoadOrder(List<Essence> loadedEssences) {
        this.loadedEssences = loadedEssences;
    }

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

        // Perform topological sorting to find correct load order
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

    // Topological sorting to get the correct loading order based on internal dependencies
    private List<String> getLoadOrder(Map<String, List<String>> dependencyGraph) {
        List<String> sortedOrder = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        Set<String> visiting = new HashSet<>();

        for (String essence : dependencyGraph.keySet()) {
            if (!visited.contains(essence)) {
                if (!dfs(essence, dependencyGraph, visited, visiting, sortedOrder)) {
                    return Collections.emptyList(); // Return empty list if a cycle is detected
                }
            }
        }
        // The DFS adds elements in reverse order, so we do not need to reverse again.
        return sortedOrder;
    }

    // DFS helper method for topological sorting
    private boolean dfs(String current, Map<String, List<String>> dependencyGraph, Set<String> visited, Set<String> visiting, List<String> sortedOrder) {
        visiting.add(current);
        for (String dependency : dependencyGraph.getOrDefault(current, Collections.emptyList())) {
            if (!visited.contains(dependency)) {
                if (visiting.contains(dependency)) {
                    // Circular dependency detected
                    return false;
                }
                if (!dfs(dependency, dependencyGraph, visited, visiting, sortedOrder)) {
                    return false;
                }
            }
        }
        visiting.remove(current);
        visited.add(current);
        sortedOrder.add(current); // Add to the load order after processing dependencies
        return true;
    }

    // Check for circular dependencies using DFS
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

    // Detect circular dependencies
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
