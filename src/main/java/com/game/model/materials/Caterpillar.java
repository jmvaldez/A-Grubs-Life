package com.game.model.materials;

import com.game.controller.Game;
import com.game.model.engine.AnimationTimer;
import com.game.model.engine.Functions;
import com.game.util.GameAudio;

import javax.swing.*;

public class Caterpillar {

    private final int maxLevel = 10;
    public Enemy engagedEnemy;
    private int health;
    private int experience;
    private int strength;
    private int level;
    private int levelMaxExp;
    private int levelMaxHealth;
    private int energy;
    private Location currentLocation;
    private boolean winner;
    private String lastAction;

    private boolean isDead;
    private ImageIcon caterpillarImageIcon;
    private ImageIcon actionImageIcon;


    public Caterpillar() {
        this.level = 1;
        this.experience = 0;
        this.levelMaxExp = 5;
        this.health = 50;
        this.levelMaxHealth = 50;
        this.energy = 20;

        this.strength = 5;
        this.isDead = false;
        this.lastAction = ">>>--------NEW LIFE!--------";
        this.winner = false;
        this.engagedEnemy = null;
        this.caterpillarImageIcon = Functions.readImage("caterpillar");
        this.actionImageIcon = null;
    }


    public ImageIcon getCaterpillarImageIcon() {
        return caterpillarImageIcon;
    }

    public boolean isDead() {
        return isDead;
    }


    public void checkDeath() {
        if (this.health <= 0) {
            this.isDead = true;
            this.caterpillarImageIcon = Functions.readImage("caterpillar");
            Game.caterpillar.setLastAction("You have died.");
            actionImageIcon = Functions.readImage("fail");
            GameAudio.playAudio("Dead");
        }
    }

    public void checkLevelImage() {
        switch (this.level) {
            case 3:
                this.caterpillarImageIcon = Functions.readImage("caterpillar2");
                GameAudio.playAudio("Powerup");
                this.setLastAction("Revolution!!!! you are now on Stage II");
                break;
            case 6:
                this.caterpillarImageIcon = Functions.readImage("caterpillar3");
                GameAudio.playAudio("Powerup");
                this.setLastAction("Revolution!!!! you achieve your final stage!!!");
                break;
            default:
                GameAudio.playAudio("Levelup");
                this.setLastAction("Level up!!!!");

        }
    }

    public void levelUp() {
        setLevel(this.level + 1);
        this.strength += 5;
        this.levelMaxHealth += 10;
        this.levelMaxExp += 3;
        this.health = levelMaxHealth;
        checkLevelImage();

    }

    public int getHealth() {
        return health;
    }


    public void setHealth(int health) {
        if (health >= this.health) {
            AnimationTimer.healthIncreaseAnimationTimer.start();
        }

        if (health <= 0) {
            this.health = 0;
        } else {
            this.health = Math.min(health, levelMaxHealth);
        }
    }


    public int getExperience() {
        return experience;
    }

    public void setExperience(int exp) {
        if (exp >= levelMaxExp) {
            //taking this portion of code out resets the experience to 0 after level up
            levelUp(); //increases level / ends the stage once appropriate level
            this.experience = 0; // reset experience to 0 after level up
        } else {
            this.experience = exp;
        }
    }

    public int getLevelMaxHealth() {
        return levelMaxHealth;
    }

    public void setLevelMaxHealth(int levelMaxHealth) {
        this.levelMaxHealth = levelMaxHealth;
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
        this.level = Math.min(level, 10);


    }

    public String getLastAction() {
        return this.lastAction;
    }

    public void setLastAction(String str) {
        this.lastAction += "<br>\n>>> " + str;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    //This Method not only set location but every components in this room!
    public void setCurrentLocation(String location) {
        this.currentLocation = Game.getLocations().get(location);
    }

    public int getLevelMaxExp() {
        return levelMaxExp;
    }

    public void setLevelMaxExp(int levelMaxExp) {
        this.levelMaxExp = levelMaxExp;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }


    public void resetLastAction() {
        this.lastAction = "";
    }

    public ImageIcon getActionImageIcon() {
        return actionImageIcon;
    }

    public void checkWin() {
        if (Game.boss.isDead()) {
            Game.initWinnerPanel();
        }
    }
}
