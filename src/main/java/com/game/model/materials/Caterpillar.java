package com.game.model.materials;

import com.game.controller.Game;

import java.util.ArrayList;
import java.util.HashMap;

public class Caterpillar {
    public boolean winner;
    private int health;
    private int experience;
    private int strength;
    private int level = 1;
    private int maxLevel = 10;
    private int maxExperience = 5;

    private Location currentLocation;
    private boolean hidden;
    private String lastAction;

    private boolean isDead;

    public Enemy getEngagedEnemy() {
        return engagedEnemy;
    }

    public void setEngagedEnemy(Enemy engagedEnemy) {
        this.engagedEnemy = engagedEnemy;
    }

    private Enemy engagedEnemy;

    public Caterpillar(int health, int experience, int strength) {
        this.health = health;
        this.experience = experience;
        this.strength = strength;
        this.hidden = false;
        this.lastAction = "";
        this.winner = false;
        this.isDead = false;
        this.engagedEnemy = null;
    }

    public boolean isDead() {
        return isDead;
    }

    public void checkDeath() {
        if (this.health <= 0) {
            this.isDead = true;
            Game.caterpillar.setLastAction("Oh dear you have died.");
        }
    }

    public Location getCurrentLocation() {
        return this.currentLocation;
    }

    public void setCurrentLocation(Location location) { //we should move this to the bottom
        currentLocation = location;
        currentLocation.setEnemies(getRandomEnemies(currentLocation));

    }

    private HashMap<String, Enemy> getRandomEnemies(Location location){
        ArrayList<String> keyList = new ArrayList<String>(Game.getEnemies().keySet());
        int enemyQty = getRandomNumber(1, 3);
        ArrayList<Integer> usedIndex = new ArrayList<Integer>();
        HashMap<String, Enemy> result = new HashMap<>();
        for (int i =0; i <= enemyQty; i++){
            while(true){
                int index = getRandomNumber(0, Game.getEnemies().size());
                if (!usedIndex.contains(index)){
                    usedIndex.add(index);
                    String name = keyList.get(index);
                    result.put(name, Game.getEnemies().get(name));
                    break;
                }
            }
        }
        return result;
    }

    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public void eat(Leaf leaf) {
        setHealth(getHealth() + 10);
        System.out.println("eat");
        //deleted some of the condition statement
        if ((getExperience() + leaf.getXp()) >= maxExperience) {
            //taking this portion of code out resets the experience to 0 after level up
            levelUp(); //increases level / ends the stage once appropriate level
            maxExperience += maxExperience; // double experience needed to level up
            setExperience(0); // reset experience to 0 after level up
        }
        //changed the else if
        else {
            setExperience(getExperience() + leaf.getXp()); // no level-up by experience up
        }

    }

    public void levelUp() {
        setStrength(strength + 50);
        setLevel(level + 1);
        if (getLevel() == 2) {
            this.setLastAction("You are level 2! You feel slightly stronger and more healthy.");
        } else if (getLevel() == 3) {
            this.setLastAction("You have reached level 3! You are now a butterfly... from now on you can use acid attacks.");
        } else if (getLevel() == maxLevel) {
            this.setLastAction("You have reached the max level of " + maxLevel + "!");
        }

    }

    public void healthRegenerator(int counter) {
        if (counter % 2934342 == 0) {
            setHealth(getHealth() + 1);
        }
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        if (health < 0) {
            this.health = 0;
        } else {
            this.health = health;
        }
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
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

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public int getMaxExperience() {
        return maxExperience;
    }
}
