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

/**
 * The EssenceLoader class is responsible for loading and managing essences.
 * An essence is a configurable system that can be loaded dynamically from JAR files.
 * It uses reflection to load and instantiate classes that implement the Essence interface.
 * The EssenceLoader class also checks the external dependencies of the essences and loads them if they are available.
 */
public class EssenceLoader {

    /**
     * The ESSENCE_FOLDER variable represents the name of the folder where essences are located.
     * It is used in the EssenceLoader class to specify the folder path for essences.
     *
     * <p>Example usage:</p>
     * <pre>{@code
     * File pluginFolder = new File(CraftEssence.getInstance().getDataFolder(), ESSENCE_FOLDER);
     * }</pre>
     */
    private static final String ESSENCE_FOLDER = "essences";
    /**
     * Represents a list of loaded essences.
     */
    private final List<Essence> loadedEssences = new ArrayList<>();
    /**
     * The `classHolder` is an instance of the `ClassHolder` class that holds a mapping of class names to their corresponding Class objects.
     * It is used to manage and retrieve Class objects associated with class names.
     *
     * <p>Usage:</p>
     * <pre>{@code
     * ClassHolder classHolder = new ClassHolder();
     * }</pre>
     *
     * @see ClassHolder
     */
    private final ClassHolder classHolder = new ClassHolder();
    /**
     * A private final field that holds an instance of the {@link JarHolder} class.
     *
     * <p>
     * Example usage:
     * <pre>{@code
     * private final JarHolder jarHolder = new JarHolder();
     * }</pre>
     * </p>
     *
     * @see JarHolder
     * @since 1.0.0
     */
    private final JarHolder jarHolder = new JarHolder();

    /**
     * The essenceClassLoader variable represents a URLClassLoader instance which is responsible for dynamically loading classes from JAR files.
     * It is used in the EssenceLoader class to load essences from JAR files stored in the essences folder.
     *
     * <p>
     * Example usage:
     * <pre>{@code
     * EssenceClassLoader essenceClassLoader = new URLClassLoader(jarHolder.getJarUrls(), this.getClass().getClassLoader());
     * }</pre>
     * </p>
     *
     * @see EssenceLoader
     * @see EssenceLoader#loadEssences()
     * @since 1.0.0
     */
    private URLClassLoader essenceClassLoader;

    /**
     * Loads the essences in a specific order based on external dependencies.
     */
    public void loadEssencesInOrder() {
        List<Essence> essences = loadEssences();

        new BukkitRunnable() {
            @Override
            public void run() {
                if (checkExternalDependencies(essences)) {
                    EssenceLogger.Info("All external dependencies are met. Loading essences...");
                    cancel();

                    LoadOrder loadOrder = new LoadOrder(essences);
                    loadOrder.loadInOrder();
                } else {
                    EssenceLogger.Info("Waiting for external dependencies...");
                }
            }
        }.runTaskTimer(CraftEssence.getInstance(), 20, 20);
    }

    /**
     * Loads the essences from the specified folder.
     *
     * @return the list of loaded essences
     */
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
                    jarHolder.addJarFile(jarFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            essenceClassLoader = new URLClassLoader(jarHolder.getJarUrls(), this.getClass().getClassLoader());

            for (File jarFile : jarFiles) {
                try (JarFile jar = new JarFile(jarFile)) {
                    jar.stream()
                            .filter(entry -> entry.getName().endsWith(".class"))
                            .forEach(this::loadEssenceClass);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            EssenceLogger.Info("No JAR files found in the essences folder.");
        }

        return loadedEssences;
    }

    /**
     * Checks the external dependencies of a list of essences.
     *
     * @param essences the list of essences to check dependencies for
     * @return true if all external dependencies are loaded, false otherwise
     */
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

    /**
     * Checks if the specified external plugin dependency is loaded.
     *
     * @param externalPlugin the name of the external plugin dependency to check
     * @return true if the external dependency is loaded, false otherwise
     */
    private boolean isExternalDependencyLoaded(String externalPlugin) {
        return Bukkit.getPluginManager().getPlugin(externalPlugin) != null;
    }

    /**
     * Loads an Essence class from a JarEntry.
     *
     * @param entry the JarEntry representing the class file
     */
    private void loadEssenceClass(JarEntry entry) {
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
