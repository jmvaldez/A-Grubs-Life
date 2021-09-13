/*
    This class is a representation of a location in the game.
 */
package com.game.model.materials;

import com.game.controller.Game;

import java.util.Random;

public class Location {
    private String name;
    private String description;
    private String north;
    private String south;
    private String east;
    private String west;
    private Leaf leaf;
    private Enemy enemy;


    public Location(String name, String description, String north, String south, String east, String west){
        this.name = name;
        this.description = description;
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
        setLeaf();

    }

    private void setLeaf() {
        Random random = new Random();

            this.leaf = new Leaf();
    }
    private void setRandomSpawnedEnemy(){

    }

    public Leaf getLeaf(){
        return this.leaf;
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

    public Enemy getEnemy() {
        return enemy;
    }
    public void setEnemy(Enemy enemy){ this.enemy = enemy;}
}
