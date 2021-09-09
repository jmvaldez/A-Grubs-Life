/*
    This is the game class. It instantiates all of the games materials, and updates them. The game will use the model package and view package to pass data from the user to the system and vice versa.
 */
package com.game.controller;


import com.game.client.Client;
import com.game.model.engine.LogicEngine;
import com.game.model.materials.Caterpillar;
import com.game.model.materials.Enemy;
import com.game.model.materials.Location;
import com.game.view.ViewWindow;
import com.sun.tools.javac.Main;

import java.io.*;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

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

    public Game() {

    }
    //This should be called by the client to start a new game.
    public void start(){
        setUpComponents();
//      Hongyi: correct autometic health increase and move the refresh window function to input panel
//        run();
    }

    //This method is designed to instantiate the necessary fields of a Game object.
    private void setUpComponents(){
        this.enemies = populateEnemies();
        this.locations = populateLocations();
        this.caterpillar = new Caterpillar(100,0,0);
        this.processor = new LogicEngine(caterpillar,locations, enemies);
        this.caterpillar.setCurrentLocation(locations.get("GENESIS"));
        this.viewWindow = new ViewWindow();
    }
    //This class controls the game loop. As the user inputs information the view will be updated.
    //I want an instructions panel to be read and you cant start the game until you hit
    private void run(){
        int counter = 0;
        while (true){
            viewWindow.updateCaterpillarStatus();
            caterpillar.healthRegenerator(counter++);
            System.out.println("1");
        }

    }

    //This is a private helper method to read in all of the locations in a text file and parse them to ingame Location objects.
    private HashMap<String,Location> populateLocations(){
        HashMap<String,Location> locations = new HashMap<>();
        String[] locationFields;
        //                locationFields = myReader.nextLine().split(",");
//
//                Location loc = new Location(locationFields[0].trim(),locationFields[ 1].trim(), locationFields[ 2].trim(), locationFields[ 3].trim(), locationFields[4].trim(),locationFields[ 5].trim() );
//                loc.setEnemy(enemies.get(locationFields[0].trim().toLowerCase(Locale.ROOT)));
//                locations.put(locationFields[0].trim(), loc);
//        File file = new File(getClass().getResource("src/com/game/locations.txt").toURI());
//            Scanner myReader = new Scanner(file);
//            while(myReader.hasNextLine()){
        try{
            InputStream inputStream = getClass().getResourceAsStream("locations.txt");
            InputStreamReader myReader = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(myReader);
            String line = null;
            while((line = br.readLine() )!= null){
                locationFields = line.split(",");

                Location loc = new Location(locationFields[0].trim(),locationFields[ 1].trim(), locationFields[ 2].trim(), locationFields[ 3].trim(), locationFields[4].trim(),locationFields[ 5].trim() );
                loc.setEnemy(enemies.get(locationFields[0].trim().toLowerCase(Locale.ROOT)));
                locations.put(locationFields[0].trim(), loc);
            }
            System.out.println(locations.toString());
            br.close();
            myReader.close();
            inputStream.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
            }
        return locations;
    }
    //This is a private helper method to populate Enemy objects from an external text file.
    private HashMap<String,Enemy> populateEnemies(){
        HashMap<String, Enemy> enemies = new HashMap<>();

        String[] enemyFields;
        try{


            InputStream inputStream = getClass().getResourceAsStream("enemies.txt");
            InputStreamReader myReader = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(myReader);
            String line = null;
            while((line = br.readLine() )!= null){
                enemyFields = line.split(",");

                Enemy enemy = new Enemy(enemyFields[0].trim(),Integer.parseInt(enemyFields[ 1].trim()), Integer.parseInt(enemyFields[ 2].trim()), Integer.parseInt(enemyFields[ 3].trim()), Boolean.parseBoolean(enemyFields[4].trim()), Boolean.parseBoolean(enemyFields[5].trim()), enemyFields[6].trim(), Boolean.parseBoolean(enemyFields[7].trim()));
                enemies.put(enemyFields[6].trim(), enemy);

            }
            System.out.println(enemies.toString());
            br.close();
            myReader.close();
            inputStream.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return enemies;
    }
    public HashMap<String, Enemy> getEnemies() {
        return enemies;
    }

}