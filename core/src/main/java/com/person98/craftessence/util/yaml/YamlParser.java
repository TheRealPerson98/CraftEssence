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

public class YamlParser {

    private static final ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
    private static final YAMLFactory YAML_FACTORY = new YAMLFactory()
            .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)
            .disable(YAMLGenerator.Feature.SPLIT_LINES)
            .disable(YAMLGenerator.Feature.MINIMIZE_QUOTES);

    private static final Gson gson = new Gson();

    // Load YAML configuration
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

    // Save YAML configuration
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
