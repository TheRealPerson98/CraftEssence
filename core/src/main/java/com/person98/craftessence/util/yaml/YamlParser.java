package com.person98.craftessence.util.yaml;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.google.gson.Gson;
import com.person98.craftessence.util.builder.IBuilder;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * The YamlParser class provides methods for loading and saving YAML files using the Jackson YAML library and Gson.
 */
public class YamlParser {

    /**
     * Utility class for reading YAML files and converting them into Java objects.
     * Uses the ObjectMapper class from the Jackson YAML library.
     */
    private static final ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
    /**
     * YAML_FACTORY is a constant variable of type YAMLFactory. It is an instance of YAMLFactory class from the Jackson YAML library.
     * The YAMLFactory instance is configured with certain features disabled using the disable() method.
     * These features affect the behavior of YAMLGenerator while generating YAML output.
     *
     * Disabling features:
     * - WRITE_DOC_START_MARKER: Disables writing of the starting "---" at the beginning of the YAML document.
     * - SPLIT_LINES: Disables splitting of long lines in the output YAML.
     * - MINIMIZE_QUOTES: Disables minimizing of quotes around simple scalar values in the output YAML.
     *
     * This variable is used within the YamlParser class for YAML parsing and serialization operations.
     *
     * Please refer to the YamlParser class for usage examples.
     */
    private static final YAMLFactory YAML_FACTORY = new YAMLFactory()
            .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)
            .disable(YAMLGenerator.Feature.SPLIT_LINES)
            .disable(YAMLGenerator.Feature.MINIMIZE_QUOTES);

    /**
     *
     */
    private static final Gson gson = new Gson();

    /**
     * Loads a YAML file and deserializes it into an object of the specified class.
     *
     * @param <T>   the type of the object to be deserialized
     * @param file  the YAML file to load
     * @param clazz the class of the object to be deserialized
     * @return the deserialized object
     * @throws IOException              if an I/O error occurs while reading the file
     * @throws FileNotFoundException    if the file does not exist
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
                return (T) builderInstance.build();  // Return the built object
            } else {
                return gson.fromJson(json, clazz);  // Regular object deserialization
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
