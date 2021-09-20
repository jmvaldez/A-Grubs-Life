/*
    This class is a representation of a location in the game.
 */
package com.game.model.materials;

import com.game.model.engine.Functions;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Locale;

public class Location {
    private String name;
    private String description;
    private String north;
    private String south;
    private String east;
    private String west;
    private static HashMap<String, Enemy> enemies;
    private HashMap<String, Item> items;
    private ImageIcon backgroundImageIcon;

    public Location(String name, String description, String north, String south, String east, String west){
        this.name = name;
        this.description = description;
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
        this.backgroundImageIcon = Functions.readImage(name.toLowerCase());

    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getNorth() {
        return this.north;
    }

    public String getSouth() {
        return this.south;
    }

    public String getEast() {
        return this.east;
    }

    public String getWest() {
        return this.west;
    }

    public void setEnemies(HashMap<String, Enemy> enemies) {
        this.enemies = enemies;
    }

    public static HashMap<String, Enemy> getEnemies() {
        return enemies;
    }

    public HashMap<String, Item> getItems() {
        return items;
    }
    public void setItems(HashMap<String, Item> items) {
        this.items = items;
    }

    public ImageIcon getBackgroundImageIcon() {
        return backgroundImageIcon;
    }
}
