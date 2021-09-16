/*
    This is the game class. It instantiates all of the games materials, and updates them. The game will use the model package and view package to pass data from the user to the system and vice versa.
 */
package com.game.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.game.model.engine.JsonReader;
import com.game.model.engine.LogicEngine;
import com.game.model.materials.Caterpillar;
import com.game.model.materials.Enemy;
import com.game.model.materials.Item;
import com.game.model.materials.Location;
import com.game.view.ViewWindow;
import com.game.view.GameAudio;


import java.io.*;
import java.util.*;

public class Game {

    public static Caterpillar caterpillar;

    private static LogicEngine processor;
    private static ViewWindow viewWindow;
    private static HashMap<String, Location> locations;
    private static HashMap<String, Enemy> enemies;
    private static HashMap<String, Item> items;




    //This should be called by the client to start a new game.

    public void start() {
        enemies = populateEnemies();
        items = populateItems();
        locations = populateLocations();

        caterpillar = new Caterpillar(100, 0, 5);
        caterpillar.setCurrentLocation(locations.get("Genesis"));
        processor = new LogicEngine();
        viewWindow = new ViewWindow();
        viewWindow.initSidePanel();
        GameAudio.PlayWelcomeAudio();

    }

    // Returns a map of locations based on external Json file
    private HashMap<String, Location> populateLocations() {

        HashMap<String, Location> locations = new HashMap<>();

        try {

            String locationsStream = JsonReader.getJsonStream("/json/Locations.json");

            JsonNode node = JsonReader.parse(locationsStream);

            Iterator<Map.Entry<String, JsonNode>> nodes = node.get("Locations").fields();

            while (nodes.hasNext()) {
                Map.Entry<String, JsonNode> entry = nodes.next();

                String roomNames = entry.getKey();
                String roomDescriptions = entry.getValue().get("description").asText();
                String north = entry.getValue().get("north").asText();
                String south = entry.getValue().get("south").asText();
                String east = entry.getValue().get("east").asText();
                String west = entry.getValue().get("west").asText();

                Location location = new Location(roomNames, roomDescriptions, north, south, east, west);
                locations.put(roomNames, location);

            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return locations;
    }

//    private String getJsonStream(String filePath) throws IOException {
//        byte[] data;
//        InputStream in = Objects.requireNonNull(getClass().getResourceAsStream(filePath));
//        data = in.readAllBytes();
//        return new String(data);
//    }

    private HashMap<String, Enemy> populateEnemies() {
        HashMap<String, Enemy> enemies = new HashMap<>();

        try {
            String enemiesStream = JsonReader.getJsonStream("/json/Enemies.json");

            // passing in the jsonLocationFile as a string to be parsed into a JsonNode
            JsonNode node = JsonReader.parse(enemiesStream);

            Iterator<Map.Entry<String, JsonNode>> nodes = node.get("Enemies").fields();

            while (nodes.hasNext()) {
                Map.Entry<String, JsonNode> entry = nodes.next();

                String enemyName = entry.getKey();
                int enemyMaxHealth = entry.getValue().get("health").asInt();
                int enemyStrength = entry.getValue().get("strength").asInt();
                int enemyExp = entry.getValue().get("exp").asInt();

                Enemy enemy = new Enemy(enemyName, enemyMaxHealth, enemyStrength);
                enemies.put(enemyName, enemy);


            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return enemies;


    }

    private HashMap<String, Item> populateItems() {
        HashMap<String, Item> items = new HashMap<>();

        try {

            String itemStream = JsonReader.getJsonStream("/json/Items.json");
            // passing in the jsonLocationFile as a string to be parsed into a JsonNode
            JsonNode node = JsonReader.parse(itemStream);


            Iterator<Map.Entry<String, JsonNode>> nodes = node.get("Items").fields();

            while (nodes.hasNext()) {
                Map.Entry<String, JsonNode> entry = nodes.next();

                String itemName = entry.getKey();
                int itemHealth = entry.getValue().get("health").asInt();
                int itemExp = entry.getValue().get("exp").asInt();
                int itemRarity = entry.getValue().get("rarity").asInt();

                Item item = new Item(itemName, itemHealth, itemExp, itemRarity);
                items.put(itemName, item);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return items;


    }

    public static HashMap<String, Location> getLocations() {
        return locations;
    }

    public static HashMap<String, Enemy> getEnemies() {
        return enemies;
    }

    public static HashMap<String, Item> getItems() {
        return items;
    }

    public static ViewWindow getViewWindow() {
        return viewWindow;
    }

    public static LogicEngine getProcessor() {
        return processor;
    }

}