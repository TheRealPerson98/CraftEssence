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
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class EssenceLoader {

    private static final String ESSENCE_FOLDER = "essences";
    private final List<Essence> loadedEssences = new ArrayList<>();
    private final ClassHolder classHolder = new ClassHolder();  // New ClassHolder to manage loaded classes
    private final JarHolder jarHolder = new JarHolder();  // New JarHolder to manage JAR files

    private URLClassLoader essenceClassLoader;

    public void loadEssencesInOrder() {
        // Load the essences
        List<Essence> essences = loadEssences();

        // Create a repeating task to check for external dependencies every 1 second
        new BukkitRunnable() {
            @Override
            public void run() {
                if (checkExternalDependencies(essences)) {
                    EssenceLogger.Info("All external dependencies are met. Loading essences...");
                    cancel();

                    // Process internal dependencies and load essences
                    LoadOrder loadOrder = new LoadOrder(essences);
                    loadOrder.loadInOrder();
                } else {
                    EssenceLogger.Info("Waiting for external dependencies...");
                }
            }
        }.runTaskTimer(CraftEssence.getInstance(), 20, 20);
    }

    // Load the essences from JAR files
    private List<Essence> loadEssences() {
        File pluginFolder = new File(CraftEssence.getInstance().getDataFolder(), ESSENCE_FOLDER);

        EssenceLogger.Info("Loading essences from " + pluginFolder.getAbsolutePath());

        if (!pluginFolder.exists() && !pluginFolder.mkdirs()) {
            EssenceLogger.Info("Failed to create essences folder.");
            return loadedEssences;
        }

        File[] jarFiles = pluginFolder.listFiles((dir, name) -> name.endsWith(".jar"));

        if (jarFiles != null && jarFiles.length > 0) {
            for (File jarFile : jarFiles) {
                try {
                    // Add each JAR file to the JarHolder
                    jarHolder.addJarFile(jarFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // Create a ClassLoader for the essences using the JAR URLs from the JarHolder
            essenceClassLoader = new URLClassLoader(jarHolder.getJarUrls(), this.getClass().getClassLoader());

            // Load classes from each JAR
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
            String[] externalDependencies = essence.getClass().getAnnotation(EssenceInfo.class).externalDependencies();
            for (String externalDependency : externalDependencies) {
                if (!isExternalDependencyLoaded(externalDependency)) {
                    EssenceLogger.Info("External dependency " + externalDependency + " is not loaded!");
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isExternalDependencyLoaded(String externalPlugin) {
        return Bukkit.getPluginManager().getPlugin(externalPlugin) != null;
    }

    // Load and instantiate essence class from a JAR entry
    private void loadEssenceClass(JarFile jarFile, JarEntry entry) {
        String className = entry.getName().replace('/', '.').replace(".class", "");
        if (!classHolder.isClassLoaded(className)) {
            try {
                Class<?> clazz = essenceClassLoader.loadClass(className);
                classHolder.addClass(className, clazz);  // Store the loaded class in ClassHolder

                if (Essence.class.isAssignableFrom(clazz)) {
                    Essence essenceInstance = (Essence) clazz.getDeclaredConstructor().newInstance();
                    Instances.register(clazz.asSubclass(Essence.class), essenceInstance);
                    loadedEssences.add(essenceInstance);
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                     InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }
}
