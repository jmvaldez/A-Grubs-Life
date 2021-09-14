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
import java.util.*;

public class Game {

    private HashMap<String, Location> locations;
    private HashMap<String, Enemy> enemies;
    public static Caterpillar caterpillar;
    private static LogicEngine processor;
    private ViewWindow viewWindow;


    public static LogicEngine getProcessor() {
        return processor;
    }

    public void setProcessor(LogicEngine processor) {
        this.processor = processor;
    }

    //This should be called by the client to start a new game.
    public void start() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        setUpComponents();
        GameAudio.PlayWelcomeAudio();
//      Hongyi: correct autometic health increase and move the refresh window function to input panel
//        run();
    }

    //This method is designed to instantiate the necessary fields of a Game object.
    private void setUpComponents() {
        this.enemies = populateEnemies();
        this.locations = populateLocations();
        this.caterpillar = new Caterpillar(100, 0, 0);
        this.processor = new LogicEngine(caterpillar, locations, enemies);
        this.caterpillar.setCurrentLocation(locations.get("Genesis"));
        this.viewWindow = new ViewWindow();
    }

    //This class controls the game loop. As the user inputs information the view will be updated.
    //I want an instructions panel to be read and you cant start the game until you hit
    private void run() {
        int counter = 0;
        while (true) {
            viewWindow.updateCaterpillarStatus();
            caterpillar.healthRegenerator(counter++);
        }

    }

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
                location.setEnemy(enemies.get(roomNames.toLowerCase()));
                locations.put(roomNames, location);

            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return locations;
    }

//    This is a private helper method to populate Enemy objects from an external text file.
    private HashMap<String, Enemy> populateEnemies() {
        HashMap<String, Enemy> enemies = new HashMap<>();

        String[] enemyFields;
        try {
            System.out.println("1");
            InputStream inputStream = getClass().getResourceAsStream("/enemies.txt");

            InputStreamReader myReader = new InputStreamReader(inputStream);
            System.out.println("2");
            BufferedReader br = new BufferedReader(myReader);
            String line = null;
            while ((line = br.readLine()) != null) {
                enemyFields = line.split(",");

                Enemy enemy = new Enemy(enemyFields[0].trim(), Integer.parseInt(enemyFields[1].trim()), Integer.parseInt(enemyFields[2].trim()), Integer.parseInt(enemyFields[3].trim()), Boolean.parseBoolean(enemyFields[4].trim()), Boolean.parseBoolean(enemyFields[5].trim()), enemyFields[6].trim(), Boolean.parseBoolean(enemyFields[7].trim()));
                enemies.put(enemyFields[6].trim(), enemy);

            }
            System.out.println(enemies.toString());
            br.close();
            myReader.close();
            inputStream.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return enemies;
    }

    public HashMap<String, Enemy> getEnemies() {
        return enemies;
    }

}