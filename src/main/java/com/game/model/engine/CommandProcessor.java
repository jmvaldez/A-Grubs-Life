package com.game.model.engine;


import com.game.client.Client;
import com.game.controller.Game;
import com.game.model.materials.Caterpillar;
import com.game.model.materials.Enemy;
import com.game.model.materials.Location;
import com.game.view.ViewWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class CommandProcessor {
    //    private Caterpillar caterpillar;
    private HashMap<String, Location> locations;
    private HashMap<String, Enemy> enemies;
    private Enemy enemy;  // shortcut for finding your enemy
    private boolean misfire;

    public CommandProcessor(HashMap<String, Location> locations, HashMap<String, Enemy> enemies) {

        this.locations = locations;
        this.enemies = enemies;

    }

    public void executeCommand(ArrayList<String> strings) {
        if (Game.caterpillar.isDead() == true) {

            Game.caterpillar.setLastAction("you are dead, you can only type restart or quit");
            String action = strings.get(1).toUpperCase(Locale.ROOT);
            if (action.matches("RESTART")) {
                Game.caterpillar = new Caterpillar(100, 0, 0);
                Game.caterpillar.setCurrentLocation(locations.get("Genesis"));
                Game.getViewWindow().updateCaterpillarStatus();

            } else if (action.matches("QUIT")) {
                System.exit(0);
            }
            ;
        } else{
            this.enemy = enemies.get(Game.caterpillar.getCurrentLocation().getName().toLowerCase());
            String action = strings.get(0).toUpperCase(Locale.ROOT);
            String focus = strings.get(1).toUpperCase(Locale.ROOT);
            this.misfire = true;
            processCommand(action, focus); // passing in to either the combat system or command menu..

        }

    }

    private void processGodMode(String focus) {
        if (focus.equalsIgnoreCase("GODMODE")) {
            Game.caterpillar.setHealth(9999999);
            Game.caterpillar.setStrength(99999999);
            Game.caterpillar.setLastAction("The Power of God him/her/itself (god is in an existential crisis) flows through you");
        }
    }

    private void processRestart(){};

    private void processCommand(String action, String focus) {

        if (enemies.containsKey(enemies.get(Game.caterpillar.getCurrentLocation().getName())) && enemies.get(Game.caterpillar.getCurrentLocation().getName().toLowerCase()).isInCombat()) {
            runCombatCheck(action, focus);
        } else {
            runProcessMenu(action, focus);
            if (misfire) {
                processCantDoThatHere();
            }
        }
    }

    private void runCombatCheck(String action, String focus) {
        if (action.toUpperCase(Locale.ROOT).equalsIgnoreCase("ATTACK")) {
            processAttack(focus);
        }
        if (focus.toUpperCase(Locale.ROOT).equalsIgnoreCase("RUN")) {
            processRun(focus);
        }
    }

    private void processCantDoThatHere() {
        Game.caterpillar.setLastAction("You can't do that here.. We don't have that ");
    }

    //This method is where to put any new commands.. each of the cases links out to the corresponding logic method... this is essentially a directory for incoming eligible commands.
    private void runProcessMenu(String action, String focus) {
        switch (action.toUpperCase(Locale.ROOT)) {
            case "GO":
                processNavigation(focus.toLowerCase());
                processGodMode(focus);
                break;
            case "EAT":
                processEating(focus);
                break;
            case "HIDE":
                processHide(focus);
                break;
            case "HELP":
                processAntAssistance(focus);
                break;
            case "LEAVE":
                processLeave(focus);
                break;
            case "ATTACK":
                processAttack(focus);
                break;
            case "RUN":
                processRun(focus);
                break;
        }
    }

    private void processTypo() {

    }

    private void processAttack(String focus) {

        enemy.setInCombat(true);
        // attacks change as we level and get more powerful
        if (focus.equalsIgnoreCase(enemy.getName())) {
            {
                misfire = false;
                if (Game.caterpillar.getLevel() == 2) {
                    enemy.setHealth(enemy.getHealth() - Game.caterpillar.getStrength() - strengthFactor() - 5);
                    Game.caterpillar.setLastAction("You attacked the " + focus + " with odor attack, sick!");
                } else if (Game.caterpillar.getLevel() == 3) {
                    enemy.setHealth(enemy.getHealth() - Game.caterpillar.getStrength() - strengthFactor() - 10);
                    Game.caterpillar.setLastAction("You attacked the " + focus + " with acid attack, burn!");
                } else if (Game.caterpillar.getLevel() == 1) {
                    enemy.setHealth(enemy.getHealth() - Game.caterpillar.getStrength() - strengthFactor());
                    Game.caterpillar.setLastAction("You attacked the " + focus + " with tackle attack, bruised!");
                }
            }
        }
        if (enemy.getHealth() <= 0) {
            enemy.setHidden(true);
            enemy.setInCombat(false);
            Game.caterpillar.setExperience(Game.caterpillar.getExperience() + 10);
            boolean check = Game.caterpillar.getLevel() == 2;
            if (enemy.getName().equalsIgnoreCase("squirrel")) {
                Game.caterpillar.setWinner(true);
            }
            Game.caterpillar.levelUp();
            if (check) {
                Game.caterpillar.setLastAction("You have defeated the mighty " + enemy.getName() + " \n " + Game.caterpillar.getLastAction());
            } else if (Game.caterpillar.isWinner()) {
                Game.caterpillar.setLastAction("You have defeated the mighty " + enemy.getName() + " \n" + "After beating the boss you find your mate! Together you can find the tree and live happily ever after \n ending the game");
            } else {
                Game.caterpillar.setLastAction("You have defeated the mighty " + enemy.getName());
            }


        } else {
            //TODO: print status of enemy
        }
        enemyAttack();
        if (Game.caterpillar.getHealth() <= 0) {
            Game.caterpillar.setLastAction("Oh dear you have died.");
        }
    }

    private void enemyAttack() {
        Game.caterpillar.setHealth(Game.caterpillar.getHealth() - enemy.getStrength());
    }

    private int strengthFactor() {
        int strength = 0;
        if (Game.caterpillar.getStrength() > enemy.getStrength()) {
            strength = 5;
        } else if (Game.caterpillar.getStrength() < enemy.getStrength()) {
            strength = -5;
        }
        return strength;
    }

    private void processAntAssistance(String focus) {
        if (focus.equalsIgnoreCase("ANT") && Game.caterpillar.getLevel() == 2) {
            //DONE : Implement "Ant can be used in combat" logic here.
            Game.caterpillar.setStrength(Game.caterpillar.getStrength() + 60);
            Game.caterpillar.setLastAction("You have received assistance from a friendly ant");
            misfire = false;
        }
    }

    private void processRun(String focus) {
        if (focus.toUpperCase(Locale.ROOT).equalsIgnoreCase("RUN")) {
            misfire = false;
            if (Game.caterpillar.getStrength() > enemy.getStrength() && enemy.isAggressive() == true) {
                Game.caterpillar.setLastAction("You ran away from the fight despite enemy efforts to subdue you");
                enemy.setHidden(true);
                enemy.setInCombat(false);
            } else if (Game.caterpillar.getStrength() > enemy.getStrength() && enemy.isAggressive() == false) {
                Game.caterpillar.setLastAction("You ran away from the fight and live another day");
                enemy.setHidden(true);
                enemy.setInCombat(false);
            } else if (Game.caterpillar.getStrength() < enemy.getStrength() && enemy.isAggressive() == false) {
                Game.caterpillar.setLastAction("You were lucky this time, the enemy gave up its pursuit");
                enemy.setHidden(true);
                enemy.setInCombat(false);
            } else {
                Game.caterpillar.setLastAction("Oh no, you can't escape, you are forced to fight!");
                enemy.setInCombat(true);
            }

        }
    }

    private void processLeave(String focus) {
    }

    private void processHide(String focus) {

        String randomBird = enemy.displayBirdRandomly();
        if (randomBird.equals("Bird")) {
            if ((enemy.getName().equalsIgnoreCase("Bird")) && (Game.caterpillar.getLevel() == 1) && (focus.equalsIgnoreCase("CATERPILLAR"))) {
                Game.caterpillar.setHealth(Game.caterpillar.getHealth() + 30);
                Game.caterpillar.setLastAction("Great job! You are hidden from the bird");
                this.misfire = false;
            }
        } else if ((enemy.getName().equalsIgnoreCase("Bird")) && (Game.caterpillar.getLevel() == 1) && (focus.equalsIgnoreCase("CATERPILLAR"))) {
            Game.caterpillar.setLastAction("The closest place to hide is in 2 mile");
            this.misfire = false;
        }
    }


    private void processEating(String focus) {
        switch (focus.toLowerCase()) {
            case "leaf":
                Game.caterpillar.eat(Game.caterpillar.getCurrentLocation().getLeaf());

                if (!Game.caterpillar.getLastAction().contains("level")) {
                    Game.caterpillar.setLastAction("You eat a leaf!");
                }

                misfire = false;
        }
    }

    private void processNavigation(String focus) {
        switch (focus) {
            case "north":
                if (!Game.caterpillar.getCurrentLocation().getNorth().trim().equalsIgnoreCase("DEAD_END")) {
                    Game.caterpillar.setCurrentLocation(locations.get(Game.caterpillar.getCurrentLocation().getNorth().trim()));
                    Game.caterpillar.setLastAction("You travel north.");
                    misfire = false;
                }


                break;
            case "south":
                if (!Game.caterpillar.getCurrentLocation().getSouth().equalsIgnoreCase("DEAD_END")) {
                    Game.caterpillar.setCurrentLocation(locations.get(Game.caterpillar.getCurrentLocation().getSouth().trim()));
                    Game.caterpillar.setLastAction("You travel south.");
                    misfire = false;
                }
                break;
            case "east":
                if (!Game.caterpillar.getCurrentLocation().getEast().equalsIgnoreCase("DEAD_END")) {
                    Game.caterpillar.setCurrentLocation(locations.get(Game.caterpillar.getCurrentLocation().getEast().trim()));
                    Game.caterpillar.setLastAction("You travel east.");

                    if (Game.caterpillar.isWinner()) {
                        Game.caterpillar.setLastAction("You have made it to safe refuge with your mate! Congratulations you've won the game. ");
                    }

                    misfire = false;
                }
                break;
            case "west":
                if (!Game.caterpillar.getCurrentLocation().getWest().equalsIgnoreCase("DEAD_END")) {
                    Game.caterpillar.setCurrentLocation(locations.get(Game.caterpillar.getCurrentLocation().getWest().trim()));
                    Game.caterpillar.setLastAction("You travel west.");
                    misfire = false;
                }
                break;
        }
    }
}
