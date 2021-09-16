package com.game.model.materials;

import com.game.model.engine.Functions;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Enemy {

    private String name;
    private int health;
    private int requiredLevelToFight;
    private boolean aggressive;
    private int strength;
    private int maxHealth;
    private ImageIcon enemyImageIcon;

    public int getExp() {
        return exp;
    }

    private int exp;
    private boolean tamable;
    private String location;
    private boolean hidden;
    private boolean inCombat;
    public Enemy(String name, int maxHealth, int strength)

    {
        this.name = name;
        this.maxHealth = maxHealth;
        this.strength = strength;
        this.enemyImageIcon = Functions.readImage(name);
    }

    public void setHealth(int health) {
        this.health = Math.max(health, 0);
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getRequiredLevelToFight() {
        return requiredLevelToFight;
    }

    public boolean isAggressive() {
        return aggressive;
    }

    public int getStrength() {
        return strength;
    }

    public boolean isTamable() {
        return tamable;
    }

    public String getLocation() {
        return location;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isInCombat() {
        return inCombat;
    }

    public void setInCombat(boolean inCombat) {
        this.inCombat = inCombat;
    }
//  Caterpillar should be able to hide when there is a bird in a room
    public String getBird() {
        return "Bird";
    }

//  To increase the probability of a bird not being selected randomly in a room. It just returns an empty string
    public String getEmptyString(){
        return "";
    }



    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public ImageIcon getEnemyImageIcon() {
        return enemyImageIcon;
    }
}
