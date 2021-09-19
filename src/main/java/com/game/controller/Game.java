/*
    This is the game class. It instantiates all of the games materials, and updates them. The game will use the model package and view package to pass data from the user to the system and vice versa.
 */
package com.game.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.game.model.engine.Functions;
import com.game.model.engine.JsonReader;
import com.game.model.engine.LogicEngine;
import com.game.model.materials.Caterpillar;
import com.game.model.materials.Enemy;
import com.game.model.materials.Item;
import com.game.model.materials.Location;
import com.game.util.GameAudio;
import com.game.view.GamePanel;
import com.game.view.WelcomePanel;
import com.game.view.testing;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Game {

    public static final int MAX_ENEMY_AND_ITEM_QTY_SETT_IN_ANIMATION_PANEL = 3;
    public static final int LOCATION_ITEM_MIN_QTY = 1;
    public static final int LOCATION_ITEM_MAX_QTY = 3;
    public static final int LOCATION_ENEMY_MIN_QTY = 1;
    public static final int LOCATION_ENEMY_MAX_QTY = 3;
    public static final int ITEM_MIN_QTY = 1;
    public static final int ITEM_MAX_QTY = 2;
    public static final String IMAGE_PATH = "/image/";


    public static Caterpillar caterpillar;
    public static JFrame window;
    private static LogicEngine processor;
    private static GamePanel gamePanel;
    private static WelcomePanel welcomePanel;
    private static HashMap<String, Location> locations;
    private static HashMap<String, Enemy> enemies;
    private static HashMap<String, Item> items;

    public static void start() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
//                try {
//                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
//                    ex.printStackTrace();
//                }
                creatWindow();
                GameAudio.playAudio("welcomeScreen");

                WelcomePanel welcomePanel = new WelcomePanel();
                window.add(welcomePanel);
                welcomePanel.requestFocusInWindow();

            }
        });
    }

    public static void initGame() {
        window.getContentPane().removeAll();
        enemies = populateEnemies();
        items = populateItems();
        locations = populateLocations();
        caterpillar = new Caterpillar();
        Functions.setCurrentLocationElement("Genesis");
        processor = new LogicEngine();
        gamePanel = new GamePanel();

        window.repaint();
    }
//    private static WelcomePanel welcomePanel;


    //This should be called by the client to start a new game.

    public static HashMap<String, Location> getLocations() {
        return locations;
    }

    public static HashMap<String, Enemy> getEnemies() {
        return enemies;
    }

    public static HashMap<String, Item> getItems() {
        return items;
    }

//    private String getJsonStream(String filePath) throws IOException {
//        byte[] data;
//        InputStream in = Objects.requireNonNull(getClass().getResourceAsStream(filePath));
//        data = in.readAllBytes();
//        return new String(data);
//    }

    public static GamePanel getViewWindow() {
        return gamePanel;
    }

    public static LogicEngine getProcessor() {
        return processor;
    }


    private static void creatWindow() {

        window = new JFrame("A Grub's Life.");
        window.setLayout(new BorderLayout());
        window.setPreferredSize(new Dimension(1024, 768));
        window.setVisible(true);
        window.setResizable(false);
        window.setFocusable(true);
//        this.window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();
    }

    // Returns a map of locations based on external Json file
    private static HashMap<String, Location> populateLocations() {

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

    private static HashMap<String, Enemy> populateEnemies() {
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

                Enemy enemy = new Enemy(enemyName, enemyMaxHealth, enemyStrength, enemyExp);
                enemies.put(enemyName, enemy);


            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return enemies;


    }

    private static HashMap<String, Item> populateItems() {
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

}