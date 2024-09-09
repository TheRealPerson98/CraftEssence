package com.person98.craftessence.loader;

import com.person98.craftessence.CraftEssence;
import com.person98.craftessence.core.Essence;
import com.person98.craftessence.core.Instances;
import com.person98.craftessence.util.annotations.EssenceInfo;
import com.person98.craftessence.util.logging.EssenceLogger;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class EssenceLoader {

    private static final String ESSENCE_FOLDER = "essences";
    private List<Essence> loadedEssences = new ArrayList<>();
    private URLClassLoader essenceClassLoader;

    // This will load essences after checking external dependencies and internal order
    public void loadEssencesInOrder() {
        // Get all essences from the folder
        List<Essence> essences = loadEssences();

        // Create a repeating task to check for external dependencies every 1 second
        new BukkitRunnable() {
            @Override
            public void run() {
                if (checkExternalDependencies(essences)) {
                    EssenceLogger.Info("All external dependencies are met. Loading essences...");
                    cancel(); // Stop the repeating task

                    // Process internal dependencies and load essences
                    LoadOrder loadOrder = new LoadOrder(essences);
                    loadOrder.loadInOrder(); // Load essences in proper order
                } else {
                    EssenceLogger.Info("Waiting for external dependencies...");
                }
            }
        }.runTaskTimer(CraftEssence.getInstance(), 20, 20); // Schedule every 1 second (20 ticks)
    }

    // Load the essences but do not enable them yet, we'll handle that after dependency checks
    private List<Essence> loadEssences() {
        // Get the plugin folder location and append "essences"
        File pluginFolder = new File(CraftEssence.getInstance().getDataFolder(), ESSENCE_FOLDER);

        EssenceLogger.Info("Loading essences from " + pluginFolder.getAbsolutePath());

        // Create the essences folder if it does not exist
        if (!pluginFolder.exists()) {
            if (pluginFolder.mkdirs()) {
                EssenceLogger.Info("Essences folder created at " + pluginFolder.getAbsolutePath());
            } else {
                EssenceLogger.Info("Failed to create essences folder at " + pluginFolder.getAbsolutePath());
                return loadedEssences;
            }
        }

        // Get all the JAR files in the essences folder
        File[] jarFiles = pluginFolder.listFiles((dir, name) -> name.endsWith(".jar"));

        if (jarFiles != null && jarFiles.length > 0) {
            // Load each JAR and find Essence classes
            List<URL> jarUrls = new ArrayList<>();
            for (File jarFile : jarFiles) {
                try {
                    jarUrls.add(jarFile.toURI().toURL());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // Create one ClassLoader for all the essences
            essenceClassLoader = new URLClassLoader(jarUrls.toArray(new URL[0]), this.getClass().getClassLoader());

            // Load each essence from the JARs
            for (File jarFile : jarFiles) {
                try (JarFile jar = new JarFile(jarFile)) {
                    jar.stream()
                            .filter(entry -> entry.getName().endsWith(".class"))
                            .forEach(entry -> loadEssenceClass(jar, entry));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            EssenceLogger.Info("No JAR files found in the essences folder.");
        }

        return loadedEssences;
    }

    // Check if external dependencies are met
    private boolean checkExternalDependencies(List<Essence> essences) {
        for (Essence essence : essences) {
            EssenceInfo info = essence.getClass().getAnnotation(EssenceInfo.class);
            if (info != null) {
                for (String externalDependency : info.externalDependencies()) {
                    if (!isExternalDependencyLoaded(externalDependency)) {
                        EssenceLogger.Info("External dependency " + externalDependency + " is not loaded!");
                        return false; // Stop loading if a dependency isn't met
                    }
                }
            }
        }
        return true;
    }

    // Check if an external plugin is loaded in the server
    private boolean isExternalDependencyLoaded(String externalPlugin) {
        return Bukkit.getPluginManager().getPlugin(externalPlugin) != null;
    }

    // Load essence class from JAR and register its instance
    private void loadEssenceClass(JarFile jarFile, JarEntry entry) {
        // Convert the jar entry name to a class name
        String className = entry.getName().replace('/', '.').replace(".class", "");
        try {
            Class<?> clazz = essenceClassLoader.loadClass(className);

            // Check if the class extends Essence
            if (Essence.class.isAssignableFrom(clazz)) {
                Essence essenceInstance = (Essence) clazz.getDeclaredConstructor().newInstance();

                // Register the Essence instance in the Instances core
                Instances.register(clazz.asSubclass(Essence.class), essenceInstance);

                // Add the instance to the loaded essences list
                loadedEssences.add(essenceInstance);
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
