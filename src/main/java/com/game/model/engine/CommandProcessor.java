package com.game.model.engine;


import com.game.controller.Game;
import com.game.model.materials.Caterpillar;
import com.game.model.materials.Enemy;
import com.game.model.materials.Item;
import com.game.model.materials.Location;
import com.game.util.GameAudio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class CommandProcessor {

    private final HashMap<String, Location> locations = Game.getLocations();

    public void executeCommand(ArrayList<String> strings) {
        String action = strings.get(0).toUpperCase(Locale.ROOT);
        String focus = strings.get(1).toUpperCase(Locale.ROOT);
        processCommand(action, focus);
        Game.caterpillar.checkDeath();

    }

    private void processCommand(String action, String focus) {
        switch (action.toUpperCase(Locale.ROOT)) {
            case "ATTACK":
                processAttack(focus);
                break;
            case "RECON":
                Game.caterpillar.engagedEnemy = Game.caterpillar.getCurrentLocation().getEnemies().get(focus.toLowerCase());
                Game.caterpillar.setLastAction("ARMY lead the way!");
                GameAudio.playAudio("Recon");
                break;
            case "GO":
                processNavigation(focus);
                break;
            case "EAT":
                processEating(focus);
                GameAudio.playAudio("Eat");
                break;
            case "DEAD":
                processDead(focus);
                break;
            case "CHEAT":
                processCheat(focus);
                GameAudio.playAudio("Cheat");
                break;
        }
    }


//    private void enemyDefeated(Enemy enemy) {
//        enemy.setHidden(true);
//        enemy.setInCombat(false);
//        caterpillar.setExperience(enemy.getExp());
//
//        //checks if enemy defeated is the squirrel to set end game criteria
//        winnerWinnerSquirrelDinner(enemy);
//
////        caterpillar.levelUp();
//
//        caterpillar.setLastAction("You have defeated the mighty " + enemy.getName() + " \n " + caterpillar.getLastAction());
//        GameAudio.PlayDefeatedAudio();
//
//    }
//
//    private void winnerWinnerSquirrelDinner(Enemy enemy) {
//        if (enemy.getName().equalsIgnoreCase("squirrel")) {
//            caterpillar.setWinner(true);
//            caterpillar.setLastAction("You have defeated the mighty " + enemy.getName() + " \n" + "After beating the boss you find your mate! Together you can find the tree and live happily ever after \n ending the game");
//        }
//    }

    private void processAttack(String focus) {
        GameAudio.PlayAttackAudio();
        Game.caterpillar.engagedEnemy = Game.caterpillar.getCurrentLocation().getEnemies().get(focus.toLowerCase());

        Enemy engagedEnemy = Game.caterpillar.engagedEnemy;
        Game.caterpillar.setHealth(Game.caterpillar.getHealth() - engagedEnemy.getStrength());
        engagedEnemy.setHealth(engagedEnemy.getHealth() - Game.caterpillar.getStrength() - damageAdjustment(engagedEnemy));
        Game.caterpillar.setLastAction("You attacked the " + engagedEnemy.getName() + " " + Game.caterpillar.getStrength() + " points " + "you received " + engagedEnemy.getStrength() + " point damage!");

        if (engagedEnemy.getHealth() == 0) {

            Game.caterpillar.setLastAction(Game.caterpillar.engagedEnemy.getName() + " defeated!!" + " you received " + Game.caterpillar.engagedEnemy.getExp() + " experience points");
            Game.caterpillar.setExperience(Game.caterpillar.getExperience() + Game.caterpillar.engagedEnemy.getExp());
            Game.caterpillar.engagedEnemy.setDead();

        }

    }

    private void processEating(String focus) {
        Item currentItem = Game.caterpillar.getCurrentLocation().getItems().get(focus.toLowerCase());
        Game.caterpillar.setLastAction("you eat a " + focus + ". you get " + currentItem.getHealth() + " health point and " + currentItem.getExp() + " exp point!");
        Game.caterpillar.setHealth(Game.caterpillar.getHealth() + currentItem.getHealth());
        Game.caterpillar.setExperience(Game.caterpillar.getExperience() + currentItem.getExp());
        currentItem.setQty(currentItem.getQty() - 1);

        if (currentItem.getQty() <= 0) {
            Game.caterpillar.getCurrentLocation().getItems().remove(currentItem.getName());
        }
    }

    private void processNavigation(String focus) {
        switch (focus.toLowerCase()) {
            case "north":
                Game.caterpillar.setLastAction("You travel north.");
                Functions.setCurrentLocationElement(Game.caterpillar.getCurrentLocation().getNorth().trim());

                break;

            case "south":
                Game.caterpillar.setLastAction("You travel south.");
                Functions.setCurrentLocationElement(Game.caterpillar.getCurrentLocation().getSouth().trim());

                break;

            case "east":
                Game.caterpillar.setLastAction("You travel east.");
                Functions.setCurrentLocationElement(Game.caterpillar.getCurrentLocation().getEast().trim());

                break;

            case "west":
                Game.caterpillar.setLastAction("You travel west.");
                Functions.setCurrentLocationElement(Game.caterpillar.getCurrentLocation().getWest().trim());

                break;

            default:
                System.out.println("CommandProcessor/processNavigation user Typed: [ go " + focus + " ], Error");
        }
    }

    private void processDead(String focus) {
        switch (focus) {
            case "RESTART":
                Game.initGame();
                GameAudio.playAudio("Reborn");
                break;
            case "QUIT":
                System.exit(0);
        }
    }

    private void processCheat(String focus) {
        switch (focus) {
            case "LEVEL":
                Game.caterpillar.levelUp();
                Game.caterpillar.setLastAction("Who is your Dady");
                break;
            case "HEALTH":
                Game.caterpillar.setHealth(Game.caterpillar.getLevelMaxHealth());
                Game.caterpillar.setLastAction("<br>Who is your Mom");
                break;
            case "STRENGTH":
                Game.caterpillar.setStrength(10);
                Game.caterpillar.setLastAction("Who is your Grandpa");
                break;
            case "AMAZON":
                Game.caterpillar.setHealth(0);
                Game.gamePanel.cheatImageLabel.setIcon(Functions.readImage("cheatAmazon"));
                AnimationTimer.cheatAmazonTimer.start();
                Game.caterpillar.setLastAction("LOL, your Manager is behind you!");
                break;

        }

    }

    // adjusts damage output for combat
    private int damageAdjustment(Enemy enemy) {
        int caterpillarStrength = Game.caterpillar.getStrength();
        int enemyStrength = enemy.getStrength();

        int adjustDamageBy = 0;

        if (caterpillarStrength > enemyStrength) {
            adjustDamageBy = 5;
        } else if (caterpillarStrength < enemyStrength) {
            adjustDamageBy = -5;
        }

        int levelAdajust = Functions.getRandomNumber(1, Game.caterpillar.getLevel()) * caterpillarStrength;
        adjustDamageBy += levelAdajust;
        return adjustDamageBy;
    }


}