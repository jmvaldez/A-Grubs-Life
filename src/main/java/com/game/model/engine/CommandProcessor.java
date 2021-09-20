package com.game.model.engine;


import com.game.controller.Game;
import com.game.model.materials.Caterpillar;
import com.game.model.materials.Enemy;
import com.game.model.materials.Item;
import com.game.model.materials.Location;
import com.game.view.GameAudio;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

public class CommandProcessor {

    public void executeCommand(ArrayList<String> strings) {
        String action = strings.get(0).toUpperCase(Locale.ROOT);
        String focus = strings.get(1).toUpperCase(Locale.ROOT);
        processCommand(action, focus);
        Game.caterpillar.checkDeath();

    }

    private void processCommand(String action, String focus) {
        switch (action.toUpperCase(Locale.ROOT)) {
            case "ATTACK":
                Game.caterpillar.engagedEnemy = Location.getEnemies().get(focus.toLowerCase());
                processAttack(Game.caterpillar.engagedEnemy);
                break;
            case "RECON":
                Game.caterpillar.engagedEnemy = Location.getEnemies().get(focus.toLowerCase());
                Game.caterpillar.setLastAction("ARMY lead the way!");
                break;
            case "GO":
                processNavigation(focus);
                enemyAttackFirst();
                GameAudio.PlayGOAudio();
                break;
            case "EAT":
                processEating(focus);
                GameAudio.PlayEatAudio();
                break;
            case "DEAD":
                processDead(focus);
                break;
            case "CHEAT":
                processCheat(focus);
                break;
        }
    }



    private void enemyAttackFirst() {
        if (Location.getEnemies() != null) {
            Map.Entry<String, Enemy> enemy = Location.getEnemies().entrySet()
                    .stream()
                    .findFirst()
                    .get();
            enemyAttack(enemy);
        } else {
            System.out.println("NULL");
        }
    }

    //
    private void enemyAttack(Map.Entry<String, Enemy> enemy) {
        if (isEnemyAttackingFirst()) {
            GameAudio.PlayAttackAudio();
            Game.caterpillar.setHealth(Game.caterpillar.getHealth() - (enemy.getValue().getStrength() + 10));
            Game.caterpillar.setLastAction("You were spotted by the " + enemy.getValue().getName() + "They attack you for BLANK damage.");
        } else {
            Game.caterpillar.setLastAction("The Enemy didn't see you");
        }
    }

    // method for random chance that an enemy will attack the player
    private boolean isEnemyAttackingFirst() {
        int min = 1;
        int max = 10;
        int chance = 5;
        int rand = Functions.getRandomNumber(min, max);
        return rand > chance;
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

    private void processAttack(Enemy enemy) {
        GameAudio.PlayAttackAudio();
        Game.caterpillar.setHealth(Game.caterpillar.getHealth() - enemy.getStrength());
        enemy.setHealth(enemy.getHealth() - Game.caterpillar.getStrength() - damageAdjustment(enemy));
        if (enemy.getHealth() == 0) {
            Game.caterpillar.setLastAction(enemy.getName() + " defeated!!" + " you received " + enemy.getExp() + " experience points");
            Game.caterpillar.setExperience(Game.caterpillar.getExperience() + enemy.getExp());
            Game.caterpillar.getCurrentLocation().getEnemies().remove(enemy.getName());
        } else {
            Game.caterpillar.setLastAction("You attacked the " + enemy.getName() + " " + Game.caterpillar.getStrength() + " points " + "you received " + enemy.getStrength() + " point damage!");
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
                Game.caterpillar = new Caterpillar();
                Functions.setCurrentLocationElement("Genesis");
                Game.caterpillar.resetLastAction();
                Game.caterpillar.setLastAction("NEW LIFE!");
                Game.getViewWindow().updateLabels();
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
        return adjustDamageBy;
    }


}