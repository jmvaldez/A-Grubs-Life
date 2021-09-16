package com.game.model.engine;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class JsonReader {

    // only need one object mapper
    private static final ObjectMapper objectMapper = getDefaultObjectMapper();

    // creating ObjectMapper in this method for configuration purposes
    private static ObjectMapper getDefaultObjectMapper() {
        ObjectMapper defaultObjectMapper = new ObjectMapper();
        defaultObjectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        return defaultObjectMapper;
    }

    // takes in json string and returns JsonNode
    public static JsonNode parse(String srcData) throws JsonProcessingException {
        return objectMapper.readTree(srcData);
    }

    /*
     * Returns the data as a String read from the file passed in.
     * getJsonStream is being used to pass the read out content to the parse() method
     * EX: JsonReader.parse(getJsonStream("Some/path/to/json"))
     */
    public static String getJsonStream(String filePath) throws IOException {
        byte[] data;
        InputStream in = Objects.requireNonNull(JsonReader.class.getResourceAsStream(filePath));
        data = in.readAllBytes();
        return new String(data);
    }
}