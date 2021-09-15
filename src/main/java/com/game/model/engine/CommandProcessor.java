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
        if (caterpillar.isDead()) {

            caterpillar.setLastAction("you are dead, you can only type restart or quit");
            GameAudio.PlayYOUADEADAudio();
            String action = strings.get(1).toUpperCase(Locale.ROOT);
            if (action.matches("RESTART")) {
                Game.caterpillar = new Caterpillar(100, 0, 0);
                Game.caterpillar.setCurrentLocation(Game.getLocations().get("Genesis"));
                Game.getViewWindow().updateCaterpillarStatus();

            } else if (action.matches("QUIT")) {
                System.exit(0);
            }
        } else {

            String action = strings.get(0).toUpperCase(Locale.ROOT);
            String focus = strings.get(1).toUpperCase(Locale.ROOT);
//          passing in to either the combat system or command menu..
            processCommand(action, focus);

        }

    }

    private void processGodMode(String focus) {
        if (focus.equalsIgnoreCase("GODMODE")) {
            caterpillar.setHealth(9999999);
            caterpillar.setStrength(99999999);
            caterpillar.setLastAction("The Power of God him/her/itself (god is in an existential crisis) flows through you");
            GameAudio.PlayGodAudio();
        }
    }

    private void processStart(String focus) {
        if (focus.equalsIgnoreCase("START")) {
            Game.getViewWindow().initSidePanel();
        }
    }

    private void processCommand(String action, String focus) {

        try {

            if (action.toUpperCase(Locale.ROOT).equalsIgnoreCase("ATTACK")) {
                caterpillar.setEngagedEnemy(caterpillar.getCurrentLocation().getEnemies().get(focus.toLowerCase()));
                processAttack(caterpillar.getEngagedEnemy());


            } else {
                Game.caterpillar.setEngagedEnemy(null);
                switch (action.toUpperCase(Locale.ROOT)) {
                    case "GO":
                        processNavigation(focus.toLowerCase());
                        processGodMode(focus);
                        processStart(focus);
                        GameAudio.PlayGOAudio();
                        break;
                    case "EAT":
                        processEating(focus);
                        GameAudio.PlayEatAudio();
                        break;
                }
            }
        } catch (Exception e) {
            processCantDoThatHere();
        }
    }



    private void processCantDoThatHere() {
        Game.caterpillar.setLastAction("You can't do that here.. We don't have that ");
        GameAudio.PlayYOUCANTAudio();
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

        if (enemy.getHealth() <= 0){
            caterpillar.getCurrentLocation().getEnemies().remove(enemy.getName());

        }
    }

    //        enemy.setInCombat(true);
    // attacks change as we level and get more powerful
//        if (focus.equalsIgnoreCase(enemy.getName())) {
//            {
//                misfire = false;
//                if (Game.caterpillar.getLevel() == 2) {
//                    enemy.setHealth(enemy.getHealth() - Game.caterpillar.getStrength() - strengthFactor() - 5);
//                    Game.caterpillar.setLastAction("You attacked the " + focus + " with odor attack, sick!");
//                } else if (Game.caterpillar.getLevel() == 3) {
//                    enemy.setHealth(enemy.getHealth() - Game.caterpillar.getStrength() - strengthFactor() - 10);
//                    Game.caterpillar.setLastAction("You attacked the " + focus + " with acid attack, burn!");
//                } else if (Game.caterpillar.getLevel() == 1) {
//                    enemy.setHealth(enemy.getHealth() - Game.caterpillar.getStrength() - strengthFactor());
//                    Game.caterpillar.setLastAction("You attacked the " + focus + " with tackle attack, bruised!");
//                }
//            }
//        }
//        if (enemy.getHealth() <= 0) {
//            enemy.setHidden(true);
//            enemy.setInCombat(false);
//            Game.caterpillar.setExperience(Game.caterpillar.getExperience() + 10);
//            boolean check = Game.caterpillar.getLevel() == 2;
//            if (enemy.getName().equalsIgnoreCase("squirrel")) {
//                Game.caterpillar.setWinner(true);
//            }
//            Game.caterpillar.levelUp();
//            if (check) {
//                Game.caterpillar.setLastAction("You have defeated the mighty " + enemy.getName() + " \n " + Game.caterpillar.getLastAction());
//            } else if (Game.caterpillar.isWinner()) {
//                Game.caterpillar.setLastAction("You have defeated the mighty " + enemy.getName() + " \n" + "After beating the boss you find your mate! Together you can find the tree and live happily ever after \n ending the game");
//            } else {
//                Game.caterpillar.setLastAction("You have defeated the mighty " + enemy.getName());
//            }
//
//
//        } else {
//            //TODO: print status of enemy
//        }
//        enemyAttack();
//        if (Game.caterpillar.getHealth() <= 0) {
//            Game.caterpillar.setLastAction("Oh dear you have died.");
//        }

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

//    private void processAntAssistance(String focus) {
//        if (focus.equalsIgnoreCase("ANT") && Game.caterpillar.getLevel() == 2) {
//            //DONE : Implement "Ant can be used in combat" logic here.
//            Game.caterpillar.setStrength(Game.caterpillar.getStrength() + 60);
//            Game.caterpillar.setLastAction("You have received assistance from a friendly ant");
//            misfire = false;
//        }
//    }

//    private void processRun(String focus) {
//        if (focus.toUpperCase(Locale.ROOT).equalsIgnoreCase("RUN")) {
//            misfire = false;
//            if (Game.caterpillar.getStrength() > enemy.getStrength() && enemy.isAggressive() == true) {
//                Game.caterpillar.setLastAction("You ran away from the fight despite enemy efforts to subdue you");
//                enemy.setHidden(true);
//                enemy.setInCombat(false);
//            } else if (Game.caterpillar.getStrength() > enemy.getStrength() && enemy.isAggressive() == false) {
//                Game.caterpillar.setLastAction("You ran away from the fight and live another day");
//                enemy.setHidden(true);
//                enemy.setInCombat(false);
//            } else if (Game.caterpillar.getStrength() < enemy.getStrength() && enemy.isAggressive() == false) {
//                Game.caterpillar.setLastAction("You were lucky this time, the enemy gave up its pursuit");
//                enemy.setHidden(true);
//                enemy.setInCombat(false);
//            } else {
//                Game.caterpillar.setLastAction("Oh no, you can't escape, you are forced to fight!");
//                enemy.setInCombat(true);
//            }
//
//        }
//    }

//    private void processLeave(String focus) {
//    }

//    private void processHide(String focus) {
//
//        String randomBird = enemy.displayBirdRandomly();
//        if (randomBird.equals("Bird")) {
//            if ((enemy.getName().equalsIgnoreCase("Bird")) && (Game.caterpillar.getLevel() == 1) && (focus.equalsIgnoreCase("CATERPILLAR"))) {
//                Game.caterpillar.setHealth(Game.caterpillar.getHealth() + 30);
//                Game.caterpillar.setLastAction("Great job! You are hidden from the bird");
//                this.misfire = false;
//            }
//        } else if ((enemy.getName().equalsIgnoreCase("Bird")) && (Game.caterpillar.getLevel() == 1) && (focus.equalsIgnoreCase("CATERPILLAR"))) {
//            Game.caterpillar.setLastAction("The closest place to hide is in 2 mile");
//            this.misfire = false;
//        }
//    }


    private void processEating(String focus) {
        Item currentItem = caterpillar.getCurrentLocation().getItems().get(focus.toLowerCase());
        caterpillar.setHealth(caterpillar.getHealth() + currentItem.getHealth());
        caterpillar.setExperience(caterpillar.getExperience() + currentItem.getExp());
        currentItem.setQty(currentItem.getQty()-1);
        if (currentItem.getQty() <= 0){
            caterpillar.getCurrentLocation().getItems().remove(currentItem.getName());
            System.out.println(caterpillar.getCurrentLocation().getItems());
        }
    }

    private void processNavigation(String focus) {
        switch (focus) {
            case "north":

                if (!caterpillar.getCurrentLocation().getNorth().trim().equalsIgnoreCase("DEAD_END")) {
                    caterpillar.setCurrentLocation(locations.get(caterpillar.getCurrentLocation().getNorth().trim()));
                    caterpillar.setLastAction("You travel north.");
                    GameAudio.PlayNORTHAudio();

                }

                break;
            case "south":
                if (!caterpillar.getCurrentLocation().getSouth().equalsIgnoreCase("DEAD_END")) {
                    caterpillar.setCurrentLocation(locations.get(caterpillar.getCurrentLocation().getSouth().trim()));
                    caterpillar.setLastAction("You travel south.");
                    GameAudio.PlaySOUTHAudio();

                }
                break;
            case "east":
                if (!caterpillar.getCurrentLocation().getEast().equalsIgnoreCase("DEAD_END")) {
                    caterpillar.setCurrentLocation(locations.get(caterpillar.getCurrentLocation().getEast().trim()));
                    caterpillar.setLastAction("You travel east.");
                    GameAudio.PlayEASTAudio();

                    if (caterpillar.isWinner()) {
                        caterpillar.setLastAction("You have made it to safe refuge with your mate! Congratulations you've won the game. ");
                        GameAudio.PlayCONGRATSAudio();
                    }

                }
                break;
            case "west":
                if (!caterpillar.getCurrentLocation().getWest().equalsIgnoreCase("DEAD_END")) {
                    caterpillar.setCurrentLocation(locations.get(caterpillar.getCurrentLocation().getWest().trim()));
                    caterpillar.setLastAction("You travel west.");
                    GameAudio.PlayWESTAudio();

                }
                break;
        }
    }
}