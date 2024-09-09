package com.person98.craftessence.util.config;

import com.person98.craftessence.CraftEssence;
import com.person98.craftessence.util.annotations.Configurable;
import com.person98.craftessence.util.logging.EssenceLogger;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

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

            Yaml yaml = createYamlInstance();
            try (FileInputStream input = new FileInputStream(configFile)) {
                Map<String, Object> data = yaml.load(input);
                T instance = clazz.getDeclaredConstructor().newInstance();
                applyConfig(instance, data);
                return instance;
            } catch (Exception e) {
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

            Yaml yaml = createYamlInstance();
            try (FileWriter writer = new FileWriter(configFile)) {
                T defaultInstance = clazz.getDeclaredConstructor().newInstance();  // Create default instance
                Map<String, Object> data = serializeToMap(defaultInstance);
                yaml.dump(data, writer);
                EssenceLogger.Info("Created default config file: " + configFile.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Apply loaded data to the instance
    private <T> void applyConfig(T instance, Map<String, Object> data) {
        Class<?> clazz = instance.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            if (data.containsKey(field.getName())) {
                try {
                    field.set(instance, data.get(field.getName()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Serialize the fields of the instance into a map
    private <T> Map<String, Object> serializeToMap(T instance) {
        Map<String, Object> data = new HashMap<>();
        Class<?> clazz = instance.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                data.put(field.getName(), field.get(instance));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    // Get the config file location for the essence
    private File getConfigFile(String fileName) {
        File essenceDataFolder = new File(CraftEssence.getInstance().getDataFolder(), "essences/data/" + essenceName);
        if (!essenceDataFolder.exists()) {
            essenceDataFolder.mkdirs();
        }
        return new File(essenceDataFolder, fileName + ".yml");
    }

    // Create a Yaml instance for serialization/deserialization
    private Yaml createYamlInstance() {
        DumperOptions options = new DumperOptions();
        options.setIndent(2);
        options.setPrettyFlow(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        return new Yaml(options);
    }
}
