package com.game.model.engine;


import com.game.controller.Game;
import com.game.model.materials.Caterpillar;
import com.game.model.materials.Enemy;
import com.game.model.materials.Item;
import com.game.model.materials.Location;
import com.game.view.GameAudio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class CommandProcessor {

    private final Caterpillar caterpillar = Game.caterpillar;
    private final HashMap<String, Location> locations = Game.getLocations();

    public void executeCommand(ArrayList<String> strings) {
        String action = strings.get(0).toUpperCase(Locale.ROOT);
        String focus = strings.get(1).toUpperCase(Locale.ROOT);
        processCommand(action, focus);
    }

    private void processCommand(String action, String focus) {
        switch (action.toUpperCase(Locale.ROOT)) {
            case "ATTACK":
                caterpillar.engagedEnemy = caterpillar.getCurrentLocation().getEnemies().get(focus.toLowerCase());
                processAttack(caterpillar.engagedEnemy);
                break;
            case "GO":
                processNavigation(focus);
                GameAudio.PlayGOAudio();
                break;
            case "EAT":
                processEating(focus);
                GameAudio.PlayEatAudio();
                break;
            case "DEAD":
                processDead(focus);
        }
    }


    private void enemyDefeated(Enemy enemy) {
        enemy.setHidden(true);
        enemy.setInCombat(false);
        caterpillar.setExperience(enemy.getExp());

        //checks if enemy defeated is the squirrel to set end game criteria
        winnerWinnerSquirrelDinner(enemy);

//        caterpillar.levelUp();

        caterpillar.setLastAction("You have defeated the mighty " + enemy.getName() + " \n " + caterpillar.getLastAction());
        GameAudio.PlayDefeatedAudio();

    }

    private void winnerWinnerSquirrelDinner(Enemy enemy) {
        if (enemy.getName().equalsIgnoreCase("squirrel")) {
            caterpillar.setWinner(true);
            caterpillar.setLastAction("You have defeated the mighty " + enemy.getName() + " \n" + "After beating the boss you find your mate! Together you can find the tree and live happily ever after \n ending the game");
        }
    }

    private void processAttack(Enemy enemy) {
        playerAttack(enemy);
        enemyAttack(enemy);

        if (enemy.getHealth() <= 0) {
            caterpillar.setExperience(caterpillar.getExperience() + enemy.getExp());
            caterpillar.getCurrentLocation().getEnemies().remove(enemy.getName());

        }
    }

    private void playerAttack(Enemy enemy) {

        int enemyDamageCalc = enemy.getHealth() - caterpillar.getStrength() - damageAdjustment(enemy);
        System.out.println("enemyDamageCalc: " + enemyDamageCalc);
        enemy.setHealth(enemyDamageCalc);
        caterpillar.setLastAction("You attacked the " + enemy.getName() + " " + caterpillar.getStrength() + " points\b" +
                "you received " + enemy.getStrength() + " point damage!");
        GameAudio.PlayAttackAudio();
    }

    private void enemyAttack(Enemy enemy) {
        caterpillar.setHealth(caterpillar.getHealth() - enemy.getStrength());
    }

    // adjusts damage output for combat
    private int damageAdjustment(Enemy enemy) {
        int caterpillarStrength = caterpillar.getStrength();
        int enemyStrength = enemy.getStrength();

        int adjustDamageBy = 0;

        if (caterpillarStrength > enemyStrength) {
            adjustDamageBy = 5;
        } else if (caterpillarStrength < enemyStrength) {
            adjustDamageBy = -5;
        }
        return adjustDamageBy;
    }

    private void processEating(String focus) {
        Item currentItem = caterpillar.getCurrentLocation().getItems().get(focus.toLowerCase());
        caterpillar.setLastAction("you eat a " + focus +". you get " + currentItem.getHealth() + " health point and " + currentItem.getExp() + " exp point!");
        caterpillar.setHealth(caterpillar.getHealth() + currentItem.getHealth());
        caterpillar.setExperience(caterpillar.getExperience() + currentItem.getExp());
        currentItem.setQty(currentItem.getQty() - 1);

        if (currentItem.getQty() <= 0) {
            caterpillar.getCurrentLocation().getItems().remove(currentItem.getName());
        }
    }

    private void processNavigation(String focus) {
        switch (focus.toLowerCase()) {
            case "north":
                caterpillar.setLastAction("You travel north.");
                caterpillar.setCurrentLocation(caterpillar.getCurrentLocation().getNorth().trim());
                GameAudio.PlayNORTHAudio();
                break;

            case "south":
                caterpillar.setLastAction("You travel south.");
                caterpillar.setCurrentLocation(caterpillar.getCurrentLocation().getSouth().trim());
                GameAudio.PlaySOUTHAudio();
                break;

            case "east":
                caterpillar.setLastAction("You travel east.");
                caterpillar.setCurrentLocation(caterpillar.getCurrentLocation().getEast().trim());
                GameAudio.PlayEASTAudio();
                break;

            case "west":
                caterpillar.setLastAction("You travel west.");
                caterpillar.setCurrentLocation(caterpillar.getCurrentLocation().getWest().trim());
                GameAudio.PlayWESTAudio();
                break;

            default:
                System.out.println("CommandProcessor/processNavigation user Typed: [ go " + focus + " ], Error");
                GameAudio.PlayICANTAudio();

        }
    }

    private void processDead(String focus){
        switch (focus){
            case "RESTART":
                Game.caterpillar = new Caterpillar(100, 0, 20);
                Game.caterpillar.setCurrentLocation("Genesis");
                Game.caterpillar.setLastAction("NEW LIFE!");
                Game.getViewWindow().updateCaterpillarStatus();
                break;
            case "QUIT":
                System.exit(0);
        }
    }
}