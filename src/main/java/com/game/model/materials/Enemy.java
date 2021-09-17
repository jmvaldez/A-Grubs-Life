package com.game.model.materials;

import com.game.model.engine.Functions;

import javax.swing.*;

public class Enemy {

    private String name;
    private int health;
    private int exp;
    private int strength;
    private int maxHealth;
    private ImageIcon enemyImageIcon;
    private ImageIcon enemyHPIcon;


    public Enemy(String name, int maxHealth, int strength, int exp) {
        this.name = name;
        this.health = maxHealth;
        this.maxHealth = maxHealth;
        this.strength = strength;
        this.exp = exp;
        this.enemyImageIcon = Functions.readImage(name);
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = Math.max(health, 0);
    }

    public int getExp() {
        return exp;
    }

    public int getStrength() {
        return strength;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public ImageIcon getEnemyImageIcon() {
        return enemyImageIcon;
    }

    public ImageIcon getCurrentEnemyHPIcon() {
        String name;
        double healthPercentage = health / (double) maxHealth;
        if (healthPercentage == 1) {
            name = "enemyhp4";
        } else if (healthPercentage > 0.75) {
            name = "enemyhp3";
        } else if (healthPercentage > 0.5) {
            name = "enemyhp2";
        } else {
            name = "enemyhp1";
        }
        return Functions.readImage(name);
    }


}

