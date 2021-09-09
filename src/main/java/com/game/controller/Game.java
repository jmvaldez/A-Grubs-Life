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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Game {
    private HashMap<String, Location> locations;
    private HashMap<String, Enemy> enemies;
    private Caterpillar caterpillar;
    private LogicEngine processor;
    private ViewWindow viewWindow;

    public Game() {

    }

    //This should be called by the client to start a new game.
    public void start() {
        setUpComponents();
        run();
    }

    //This method is designed to instantiate the necessary fields of a Game object.
    private void setUpComponents() {
        this.enemies = populateEnemies();
        this.locations = populateLocations();
        this.caterpillar = new Caterpillar(100, 0, 0);
        this.processor = new LogicEngine(caterpillar, locations, enemies);
        this.caterpillar.setCurrentLocation(locations.get("GENESIS"));
        this.viewWindow = new ViewWindow(caterpillar, processor);
    }

    //This class controls the game loop. As the user inputs information the view will be updated.
    //I want an instructions panel to be read and you cant start the game until you hit
    private void run() {
        int counter = 0;
        viewWindow.welcomeMessage();
        while (true) {
            viewWindow.updateCaterpillarStatus();
            caterpillar.healthRegenerator(counter++);
        }

    }

    //This is a private helper method to read in all of the locations in a text file and parse them to ingame Location objects.
    private HashMap<String, Location> populateLocations() {
        HashMap<String, Location> locations = new HashMap<>();
        String[] locationFields;
        ArrayList<String> rooms = new ArrayList<>();
        ArrayList<String> descriptions = new ArrayList<>();
        //                locationFields = myReader.nextLine().split(",");
//
//                Location loc = new Location(locationFields[0].trim(),locationFields[ 1].trim(), locationFields[ 2].trim(), locationFields[ 3].trim(), locationFields[4].trim(),locationFields[ 5].trim() );
//                loc.setEnemy(enemies.get(locationFields[0].trim().toLowerCase(Locale.ROOT)));
//                locations.put(locationFields[0].trim(), loc);
//        File file = new File(getClass().getResource("src/com/game/locations.txt").toURI());
//            Scanner myReader = new Scanner(file);
//            while(myReader.hasNextLine()){
        try {
            String file = "src/main/resources/GrubsLife_Locations.json";
            JsonNode node = JsonReader.parse(JsonReader.readFileAsString(file));

            Iterator<Map.Entry<String, JsonNode>> nodes = node.get("Locations").fields();
//            node.at("/Locations").fields().forEachRemaining(entry -> {
//                System.out.println("Key --> " + entry.getKey() + "\nvalue --> " + entry.getValue());
//            });

            while (nodes.hasNext()) {
                Map.Entry<String, JsonNode> entry = (Map.Entry<String, JsonNode>) nodes.next();
//                System.out.println("entry " + entry);
                String roomNames = entry.getKey();
                String roomDescriptions = entry.getValue().get("description").asText();
                String north = entry.getValue().get("north").asText();
                String south = entry.getValue().get("south").asText();
                String east = entry.getValue().get("east").asText();
                String west = entry.getValue().get("west").asText();
                rooms.add(roomNames);
                descriptions.add(roomDescriptions);
                System.out.println("Key --> " + entry.getKey() + "\nvalue --> " + entry.getValue());

            }
            System.out.println(rooms);
            System.out.println(descriptions);
//-- old stuff start
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("locations.txt");
            InputStreamReader myReader = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(myReader);
            String line = null;
            while ((line = br.readLine()) != null) {
                locationFields = line.split(",");

                Location loc = new Location(locationFields[0].trim(), locationFields[1].trim(), locationFields[2].trim(), locationFields[3].trim(), locationFields[4].trim(), locationFields[5].trim());
                // 2 = North, 3 = South, 4 = east, 5 = west
                loc.setEnemy(enemies.get(locationFields[0].trim().toLowerCase(Locale.ROOT)));
                locations.put(locationFields[0].trim(), loc);
                //System.out.println("LOC " + locationFields[2].trim());
            }

            br.close();
            myReader.close();
            inputStream.close();
//--old stuff end

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return locations;
    }

    //This is a private helper method to populate Enemy objects from an external text file.
    private HashMap<String, Enemy> populateEnemies() {
        HashMap<String, Enemy> enemies = new HashMap<>();

        String[] enemyFields;
        try {


            InputStream inputStream = getClass().getResourceAsStream("/enemies.txt");
            InputStreamReader myReader = new InputStreamReader(inputStream);
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