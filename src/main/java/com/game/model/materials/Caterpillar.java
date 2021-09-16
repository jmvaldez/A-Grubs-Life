package com.game.model.materials;

import com.game.controller.Game;
import com.game.model.engine.Functions;
import com.game.view.GameAudio;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Caterpillar {

    private int health;
    private int experience;
    private int strength;
    private int level = 1;
    private int maxLevel = 10;
    private int maxExperience = 5;

    private Location currentLocation;
    private boolean winner;
    private String lastAction;

    private boolean isDead;
    private ImageIcon caterpillarImageIcon;
    public Enemy engagedEnemy = null;

    public Caterpillar(int health, int experience, int strength) {
        this.health = health;
        this.experience = experience;
        this.strength = strength;
        this.isDead = false;
        this.lastAction = "";
        this.winner = false;
        this.engagedEnemy = null;
        this.caterpillarImageIcon = Functions.readImage("caterpillar");
    }

//    public Enemy getEngagedEnemy() {
//        return engagedEnemy;
//    }
//
//    public void setEngagedEnemy(Enemy engagedEnemy) {
//        this.engagedEnemy = engagedEnemy;
//    }

    public ImageIcon getCaterpillarImageIcon() {
        return caterpillarImageIcon;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public void checkDeathAndImage() {
        if (this.health <= 0) {
            this.isDead = true;
            Game.caterpillar.setLastAction("Oh dear you have died.");
        }
        if (3 < this.level && this.level < 7) {
            this.caterpillarImageIcon = Functions.readImage("caterpillar");
        }
        if (this.level >= 7) {
            this.caterpillarImageIcon = Functions.readImage("caterpillar");
        }
    }

    public Location getCurrentLocation() {
        return this.currentLocation;
    }

    public void setCurrentLocation(String location) { //we should move this to the bottom

        switch (location){
            case "DEAD_END":
                Game.caterpillar.setLastAction("Dead end, find another direction, check MAP!");
                break;
            case "Genesis":
                currentLocation = Game.getLocations().get("Genesis");
                currentLocation.setItems(Functions.getRandomItems());
                currentLocation.setEnemies(new HashMap<>());
                break;
            default:
                currentLocation = Game.getLocations().get(location);
                currentLocation.setEnemies(Functions.getRandomEnemies());
                currentLocation.setItems(Functions.getRandomItems());
        }
    }

    public void levelUp() {
        setStrength(strength + 50);
        setLevel(level + 1);
        if (getLevel() == 2) {
            this.setLastAction("You are level 2! You feel slightly stronger and more healthy.");
            GameAudio.PlayLEVEL2Audio();
        } else if (getLevel() == 3) {
            this.setLastAction("You have reached level 3! You are now a butterfly... from now on you can use acid attacks.");
            GameAudio.PlayBUTTERFLYAudio();
        } else if (getLevel() == maxLevel) {
            this.setLastAction("You have reached the max level of " + maxLevel + "!");
            GameAudio.PlayMAXLEVELAudio();
        }

    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = Math.max(health, 0);

    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        if ((getExperience() + experience) >= maxExperience) {
            //taking this portion of code out resets the experience to 0 after level up
            levelUp(); //increases level / ends the stage once appropriate level
            maxExperience += maxExperience; // double experience needed to level up
            this.experience = 0; // reset experience to 0 after level up
        } else {
            this.experience += experience;
        }
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getLastAction() {
        return this.lastAction;
    }

    public void setLastAction(String str) {
        this.lastAction = str;
    }

    public int getMaxExperience() {
        return maxExperience;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }
}
