package com.person98.craftessence.util.config;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.person98.craftessence.CraftEssence;
import com.person98.craftessence.util.annotations.Configurable;
import com.person98.craftessence.util.logging.EssenceLogger;
import com.person98.craftessence.util.yaml.YamlParser;

import java.io.File;
import java.io.IOException;

/**
 * The ConfigHandler class is responsible for loading and saving configuration files based on annotations.
 * It supports loading and saving YAML files using the YamlParser class.
 */
public class ConfigHandler {

    /**
     * The essenceName variable represents the name of an essence.
     */
    private final String essenceName;

    /**
     * The ConfigHandler class is responsible for loading and saving configuration files based on annotations.
     * It supports loading and saving YAML files using the YamlParser class.
     */
    public ConfigHandler(String essenceName) {
        this.essenceName = essenceName;
    }

    /**
     * Loads a configuration object for a specific class if it is annotated with @Configurable.
     *
     * @param <T>   the type of the configuration class
     * @param clazz the class of the configuration object
     * @return the loaded configuration object
     */
    public <T> T loadConfig(Class<T> clazz) {
        Configurable configurable = clazz.getAnnotation(Configurable.class);
        if (configurable != null) {
            String fileName = configurable.fileName();
            File configFile = getConfigFile(fileName);

            if (!configFile.exists()) {
                // If the file doesn't exist, create it with default values
                saveDefaultConfig(clazz);
            }

            try {
                T configInstance = YamlParser.loadYaml(configFile, clazz);
                EssenceLogger.Info("Loaded " + fileName + ".yml for " + essenceName);
                return configInstance;
            } catch (IOException e) {
                EssenceLogger.Error("Failed to load YAML file: " + configFile.getPath());
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * Saves the default configuration for the given class.
     *
     * @param clazz the class that is annotated with {@link Configurable}
     * @param <T>   the type of the class
     */
    public <T> void saveDefaultConfig(Class<T> clazz) {
        Configurable configurable = clazz.getAnnotation(Configurable.class);
        if (configurable != null) {
            String fileName = configurable.fileName();
            File configFile = getConfigFile(fileName);

            try {
                T defaultInstance = clazz.getDeclaredConstructor().newInstance();
                YamlParser.saveYaml(configFile, defaultInstance);
                EssenceLogger.Info("Created default " + fileName + ".yml for " + essenceName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Retrieves the configuration file for the given file name.
     *
     * @param fileName the name of the configuration file (without file extension)
     * @return the File object representing the configuration file
     */
    private File getConfigFile(String fileName) {
        File essenceDataFolder = new File(CraftEssence.getInstance().getDataFolder(), "data/" + essenceName);
        if (!essenceDataFolder.exists()) {
            essenceDataFolder.mkdirs();
        }
        return new File(essenceDataFolder, fileName + ".yml");
    }
}
