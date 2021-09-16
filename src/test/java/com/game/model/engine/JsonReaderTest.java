package com.game.model.engine;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class JsonReaderTest {
    String json = "{\"name\": \"Tester Json\"}";

    @Test
    public void testParse_ParsesJsonStringIntoJsonNode() throws JsonProcessingException {
        JsonNode node = JsonReader.parse(json);
        assertEquals(node.get("name").asText(), "Tester Json");
    }

    @Test
    public void testGetJsonStream_returnsContentOfFilePassedIn() {
        File file;
        try {
            file = File.createTempFile("tmp.json", null);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}