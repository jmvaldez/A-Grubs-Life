package com.game.model.engine;


import com.game.controller.Game;
import com.game.model.materials.Enemy;
import com.game.model.materials.Item;
import com.game.model.materials.Location;
import com.game.util.GameAudio;
import com.game.view.HelpWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.TimerTask;

public class CommandProcessor {

    private static Timer attackAnimationTimer;

    public void executeCommand(ArrayList<String> strings) {
        String action = strings.get(0).toUpperCase(Locale.ROOT);
        String focus = strings.get(1).toUpperCase(Locale.ROOT);
        processCommand(action, focus);
    }

    private void processCommand(String action, String focus) {
        switch (action.toUpperCase(Locale.ROOT)) {
            case "ATTACK":
                if (focus.equalsIgnoreCase(Game.boss.getName())) {
                    Game.caterpillar.engagedEnemy = Game.boss;
                } else {
                    Game.caterpillar.engagedEnemy = Location.getEnemies().get(focus.toLowerCase());
                }
                startAttackAnimation();
                break;
            case "RECON":
                if (focus.equalsIgnoreCase(Game.boss.getName())) {
                    Game.caterpillar.engagedEnemy = Game.boss;
                } else {
                    Game.caterpillar.engagedEnemy = Location.getEnemies().get(focus.toLowerCase());
                }
                Game.caterpillar.setLastAction("ARMY lead the way!");
                Game.getGamePanel().actionAnimationLabel.setIcon(Functions.readImage("recon"));
                AnimationTimer.startActionImageTimer(3000);
                GameAudio.playAudio("Recon");
                break;
            case "GO":
                Game.caterpillar.engagedEnemy = null;
                processNavigation(focus);
                enemyAttackFirst();
                GameAudio.PlayGOAudio();
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
            case "GET":
                HelpWindow.createHelpWindow();
                break;

        }
    }


    /*
     * enemyAttackFirst() executes the enemy attack if there is an enemy in the current area
     * also based on chanceForAction in enemyCalcAttack
     */
    private void enemyAttackFirst() {
        if (!Location.getEnemies().isEmpty()) {
            Map.Entry<String, Enemy> enemy = Location.getEnemies().entrySet()
                    .stream()
                    .findFirst()
                    .get();
            enemyAttackCalc(enemy);
            //check player death for enemy attack
            Game.caterpillar.checkDeath();

        } else {
            Game.caterpillar.setLastAction("No enemies in this area.");
        }
    }

    /*
     * enemyAttackCalc() does the damage calculation if the enemy succeeded in attacking first
     * if chance for action is true then we subtract player health based on enemy strength plus a surprise hit bonus
     * if chance for action is false then the enemy fails to attack and a message is displayed
     */
    private void enemyAttackCalc(Map.Entry<String, Enemy> enemy) {
        if (Functions.chanceForAction(1, 10, 5)) {
            int surpriseHitBonus = 5;
            GameAudio.PlayAttackAudio();
            Game.caterpillar.setHealth(Game.caterpillar.getHealth() - (enemy.getValue().getStrength() + surpriseHitBonus));
            String enemyAttackBuilder = "You were spotted by the " +
                    enemy.getValue().getName() +
                    "！ They attack you for " +
                    enemy.getValue().getStrength() +
                    " damage";
            Game.caterpillar.setLastAction(enemyAttackBuilder);
        } else {
            Game.caterpillar.setLastAction("The Enemy didn't see you");
        }
    }


    private void startAttackAnimation() {
        setAttackAnimationTimer();
        TimerTask startAttackAnimation = new TimerTask() {
            @Override
            public void run() {
                attackAnimationTimer.start();
            }
        };
        new java.util.Timer().schedule(startAttackAnimation, 500);

    }

    private void setAttackAnimationTimer() {

        GameAudio.playAudio("GO");
        int destinationXpos;
        int destinationYpos;
        int incrementXpos;
        int incrementYpos;

        switch (Game.caterpillar.engagedEnemy.getName().toUpperCase()) {
            case "BIRD":
                destinationXpos = 100;
                destinationYpos = 100;
                incrementXpos = (destinationXpos - 230) / 10;
                incrementYpos = (destinationYpos - 190) / 10;
                break;
            default:
                destinationXpos = Game.caterpillar.engagedEnemy.getLocation()[0];
                destinationYpos = Game.caterpillar.engagedEnemy.getLocation()[1];
                incrementXpos = (destinationXpos - 230) / 10;
                incrementYpos = (destinationYpos - 190) / 10;
                break;
        }

        int finalDestinationYpos = destinationYpos;


        attackAnimationTimer = new Timer(100, new ActionListener() {
            int startXpos = 210;
            int startYpos = 190;

            @Override
            public void actionPerformed(ActionEvent e) {
                moveCaterpillarImageLabel();
                Game.getGamePanel().repaint();
            }

            private void moveCaterpillarImageLabel() {
                if (startYpos >= finalDestinationYpos + 20) {
                    startXpos += incrementXpos;
                    startYpos += incrementYpos;
                    Game.getGamePanel().caterpillarImageLabel.setBounds(startXpos, startYpos, 100, 100);
                } else {
                    processAttack();
                    Game.getGamePanel().caterpillarImageLabel.setBounds(210, 190, 100, 100);
                    Game.getGamePanel().updateLabels();
                    attackAnimationTimer.stop();
                }
            }
        });
    }

    private void processAttack() {

        GameAudio.PlayAttackAudio();
        Enemy engagedEnemy = Game.caterpillar.engagedEnemy;
        Game.caterpillar.setHealth(Game.caterpillar.getHealth() - engagedEnemy.getStrength());
        engagedEnemy.setHealth(engagedEnemy.getHealth() - Game.caterpillar.getStrength() - damageAdjustment(engagedEnemy));
        Game.caterpillar.setLastAction("You attacked the " + engagedEnemy.getName() + " " + Game.caterpillar.getStrength() + " points " + "you received " + engagedEnemy.getStrength() + " point damage!");

        if (engagedEnemy.getHealth() == 0) {

            Game.caterpillar.setLastAction(Game.caterpillar.engagedEnemy.getName() + " defeated!!" + " you received " + Game.caterpillar.engagedEnemy.getExp() + " experience points");
            Game.caterpillar.setExperience(Game.caterpillar.getExperience() + Game.caterpillar.engagedEnemy.getExp());
            Game.caterpillar.engagedEnemy.setDead();

        }

        Game.caterpillar.checkWin();
        Game.caterpillar.checkDeath();

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
                Game.caterpillar.setStrength(Game.caterpillar.getStrength() + 10);
                Game.caterpillar.setLastAction("Who is your Grandpa");
                break;
            case "AMAZON":
                Game.caterpillar.setHealth(0);
                Game.getGamePanel().actionAnimationLabel.setIcon(Functions.readImage("cheatAmazon"));
                AnimationTimer.startActionImageTimer(3000);
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

        int levelAdjust = Functions.getRandomNumber(1, Game.caterpillar.getLevel()) * caterpillarStrength;
        adjustDamageBy += levelAdjust;
        return adjustDamageBy;
    }


}