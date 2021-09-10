package com.game.model.engine;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonReader {

    // only need one object mapper
    private static final ObjectMapper objectMapper = getDefaultObjectMapper();

    // creating ObjectMapper in this method for configuration purposes
    private static ObjectMapper getDefaultObjectMapper(){
        ObjectMapper defaultObjectMapper = new ObjectMapper();
        defaultObjectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        return defaultObjectMapper;
    }

    // takes in json string and returns JsonNode
    public static JsonNode parse(String srcData) throws JsonProcessingException {
        return objectMapper.readTree(srcData);
    }

    // stringifies the passed in file
    public static String stringifyFile(String file) throws IOException {
        return new String(Files.readAllBytes(Path.of(file)));
    }
}