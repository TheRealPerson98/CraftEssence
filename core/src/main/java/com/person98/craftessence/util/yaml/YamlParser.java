package com.person98.craftessence.util.yaml;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.person98.craftessence.util.builder.IBuilder;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * The YamlParser class provides methods for loading and saving YAML files using the Jackson YAML library and Gson.
 */
public class YamlParser {

    private static final ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());

    private static final YAMLFactory YAML_FACTORY = new YAMLFactory()
            .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)
            .disable(YAMLGenerator.Feature.SPLIT_LINES)
            .disable(YAMLGenerator.Feature.MINIMIZE_QUOTES);

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(ItemStack.class, new ItemStackAdapter()) // Register the ItemStackAdapter
            .create();

    /**
     * Loads a YAML file and deserializes it into an object of the specified class.
     *
     * @param <T>   the type of the object to be deserialized
     * @param file  the YAML file to load
     * @param clazz the class of the object to be deserialized
     * @return the deserialized object
     * @throws IOException if an I/O error occurs while reading the file
     */
    public static <T> T loadYaml(File file, Class<T> clazz) throws IOException {
        if (!file.exists()) {
            throw new FileNotFoundException("YAML file not found: " + file.getPath());
        }

        try (Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
            Object rawObject = yamlReader.readValue(reader, Object.class);
            ObjectMapper jsonMapper = new ObjectMapper();
            String json = jsonMapper.writeValueAsString(rawObject);

            // Check if the class is a builder
            if (IBuilder.class.isAssignableFrom(clazz)) {
                IBuilder<?> builderInstance = (IBuilder<?>) gson.fromJson(json, clazz);
                return (T) builderInstance.build();
            } else {
                return gson.fromJson(json, clazz);
            }
        }
    }

    /**
     * Saves an object as YAML to a specified file.
     *
     * @param file   the file to save the YAML to
     * @param object the object to be saved as YAML
     * @throws IOException if an IO error occurs while saving the YAML
     */
    public static void saveYaml(File file, Object object) throws IOException {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }

        ObjectMapper jsonMapper = new ObjectMapper();
        String json = gson.toJson(object);
        JsonNode jsonNodeTree = jsonMapper.readTree(json);

        new YAMLMapper(YAML_FACTORY).writeValue(file, jsonNodeTree);
    }
}
