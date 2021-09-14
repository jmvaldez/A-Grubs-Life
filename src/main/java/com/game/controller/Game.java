/*
    This is the game class. It instantiates all of the games materials, and updates them. The game will use the model package and view package to pass data from the user to the system and vice versa.
 */
package com.game.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.game.model.engine.JsonReader;
import com.game.model.engine.LogicEngine;
import com.game.model.materials.Caterpillar;
import com.game.model.materials.Enemy;
import com.game.model.materials.Location;
import com.game.view.ViewWindow;
import com.game.view.GameAudio;


import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Game {

    public static HashMap<String, Location> getLocations() {
        return locations;
    }

    public void setLocations(HashMap<String, Location> locations) {
        this.locations = locations;
    }

    public void setEnemies(HashMap<String, Enemy> enemies) {
        this.enemies = enemies;
    }

    private static HashMap<String, Location> locations;
    private static HashMap<String, Enemy> enemies;
    public static Caterpillar caterpillar;
    private static LogicEngine processor;

    public static ViewWindow getViewWindow() {
        return viewWindow;
    }

    private static ViewWindow viewWindow;


    public static LogicEngine getProcessor() {
        return processor;
    }

    public void setProcessor(LogicEngine processor) {
        this.processor = processor;
    }

    //This should be called by the client to start a new game.
    public void start()  {
        setUpComponents();
        GameAudio.PlayWelcomeAudio();
//      Hongyi: correct autometic health increase and move the refresh window function to input panel
//        run();
    }

    //This method is designed to instantiate the necessary fields of a Game object.
    private void setUpComponents() {
        this.enemies = populateEnemies();
        this.locations = populateLocations();
        this.caterpillar = new Caterpillar(100, 0, 5);
        this.processor = new LogicEngine();
        this.caterpillar.setCurrentLocation(locations.get("Genesis"));
        this.viewWindow = new ViewWindow();
    }

    //This class controls the game loop. As the user inputs information the view will be updated.

    // Returns a map of locations based on external Json file
    private HashMap<String, Location> populateLocations() {
        HashMap<String, Location> locations = new HashMap<>();

        try {
            String jsonLocationFile = "src/main/resources/GrubsLife_Locations.json";

            // passing in the jsonLocationFile as a string to be parsed into a JsonNode
            JsonNode node = JsonReader.parse(JsonReader.stringifyFile(jsonLocationFile));


            Iterator<Map.Entry<String, JsonNode>> nodes = node.get("Locations").fields();

            while (nodes.hasNext()) {
                Map.Entry<String, JsonNode> entry = (Map.Entry<String, JsonNode>) nodes.next();

                String roomNames = entry.getKey();
                String roomDescriptions = entry.getValue().get("description").asText();
                String north = entry.getValue().get("north").asText();
                String south = entry.getValue().get("south").asText();
                String east = entry.getValue().get("east").asText();
                String west = entry.getValue().get("west").asText();

                Location location = new Location(roomNames, roomDescriptions, north, south, east, west);
//                location.setEnemy(enemies.get(roomNames.toLowerCase()));
                locations.put(roomNames, location);

            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return locations;
    }

    private HashMap<String, Enemy> populateEnemies() {
        HashMap<String, Enemy> enemies = new HashMap<>();

        try {
            String jsonLocationFile = "src/main/resources/Enemies.json";

            // passing in the jsonLocationFile as a string to be parsed into a JsonNode
            JsonNode node = JsonReader.parse(JsonReader.stringifyFile(jsonLocationFile));


            Iterator<Map.Entry<String, JsonNode>> nodes = node.get("Enemies").fields();

            while (nodes.hasNext()) {
                Map.Entry<String, JsonNode> entry = (Map.Entry<String, JsonNode>) nodes.next();

                String enemyName = entry.getKey();
                int enemyHealth = entry.getValue().get("health").asInt();
                int enemyStrength = entry.getValue().get("strength").asInt();

                Enemy enemy = new Enemy(enemyName, enemyHealth,enemyStrength);


                enemies.put(enemyName, enemy);



            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return enemies;


    }

    public static HashMap<String, Enemy> getEnemies() {
        return enemies;
    }

}