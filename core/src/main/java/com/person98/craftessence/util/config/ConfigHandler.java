package com.person98.craftessence.util.config;

import com.person98.craftessence.CraftEssence;
import com.person98.craftessence.util.annotations.Configurable;
import com.person98.craftessence.util.builder.IBuilder;
import com.person98.craftessence.util.logging.EssenceLogger;
import com.person98.craftessence.util.yaml.YamlParser;

import java.io.File;
import java.io.IOException;

public class ConfigHandler {

    private final String essenceName;

    public ConfigHandler(String essenceName) {
        this.essenceName = essenceName;
    }

    // Load configuration for a specific class
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
                e.printStackTrace();
            }
        }
        return null;
    }

    // Save default configuration if the config file doesn't exist
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

    // Get the config file location for the essence
    private File getConfigFile(String fileName) {
        File essenceDataFolder = new File(CraftEssence.getInstance().getDataFolder(), "data/" + essenceName);
        if (!essenceDataFolder.exists()) {
            essenceDataFolder.mkdirs();
        }
        return new File(essenceDataFolder, fileName + ".yml");
    }
}
