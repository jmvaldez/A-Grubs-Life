package com.game.model.engine;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;

import static org.junit.Assert.*;

public class JsonReaderTest {
    String json = "{\"name\": \"Tester Json\"}";

    @Test
    public void parsesJsonStringIntoJsonNode() throws JsonProcessingException {
        JsonNode node = JsonReader.parse(json);
        assertEquals(node.get("name").asText(), "Tester Json");
    }
}