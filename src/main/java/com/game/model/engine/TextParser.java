package com.game.model.engine;

import com.game.controller.Game;
import com.game.exception.DeadPlayerInputException;
import com.game.exception.LivePlayerInputException;
import com.game.model.materials.Enemy;

import java.util.*;

public class TextParser {
    private HashSet<String> verbs;
    private HashSet<String> directions;
    private HashSet<String> items;
    private HashSet<String> enemies;
    private HashSet<String> cheatCode;
    private HashSet<String> deadComand;
    private String[] inputArray;


    public TextParser() {
        super();
        populateVerbs();
        populateDirection();
        populateCheatCode();
        populateDeadComand();

    }

    //If we dont get a viable verb and noun then we will pass null.
    public ArrayList<String> parserLiveInput(String input) {
        populateItem();
        populateEnemy();
        ArrayList<String> result = new ArrayList<>();
        inputArray = input.toUpperCase(Locale.ROOT).split(" ");

        if (inputArray[0].equalsIgnoreCase("HELP") && inputArray.length == 1) {
            result.add(0, "get");
            result.add(1, "help");
        } else if (inputArray.length != 2) {
            Game.caterpillar.setLastAction("TWO words Command ONLY!");
            throw new LivePlayerInputException();
        } else {
            for (String str : inputArray) {
                String otherStr = theOtherString(str);
                switch (str.toUpperCase()) {
                    case "GO":
                        if (directions.contains(otherStr) && !str.equalsIgnoreCase(otherStr)) {
                            result.add(0, str);
                            result.add(1, otherStr);
                            return result;
                        } else {
                            Game.caterpillar.setLastAction("You can not go " + otherStr + "!! Go North/South/East/West wherever is showing on the MAP!");
                            throw new LivePlayerInputException();
                        }
                    case "EAT":
                        if (items.contains(otherStr) && !str.equalsIgnoreCase(otherStr)) {
                            result.add(0, str);
                            result.add(1, otherStr);
                            return result;
                        } else {
                            Game.caterpillar.setLastAction("Can you eat " + otherStr + "?! Check the item list!");
                            throw new LivePlayerInputException();
                        }
                    case "ATTACK":
                        if (enemies.contains(otherStr) && !str.equalsIgnoreCase(otherStr)) {
                            result.add(0, str);
                            result.add(1, otherStr);
                            return result;
                        } else {
                            Game.caterpillar.setLastAction("Is " + otherStr + " a valid Enemy?! Check the enemy list!");
                            throw new LivePlayerInputException();
                        }
                    case "RECON":
                        if (enemies.contains(otherStr) && !str.equalsIgnoreCase(otherStr)) {
                            result.add(0, str);
                            result.add(1, otherStr);
                            return result;
                        } else {
                            Game.caterpillar.setLastAction("Is " + otherStr + " a lived Enemy?! Check the enemy list!");
                            throw new LivePlayerInputException();
                        }

                    case "CHEAT":
                        if (cheatCode.contains(otherStr) && !str.equalsIgnoreCase(otherStr)) {
                            result.add(0, str);
                            result.add(1, otherStr);
                            return result;
                        } else {
                            Game.caterpillar.setLastAction("Nice Try, Your Cheating Code is not Valid");
                            throw new LivePlayerInputException();
                        }
                    default:
                        Game.caterpillar.setLastAction("[ " + str + " " + otherStr + " ]" + " is not a valid command!");

                }
            }
        }
        return result;
    }

    public ArrayList<String> parserDeadInput(String input) {
        ArrayList<String> result = new ArrayList<>();

        if (deadComand.contains(input.toUpperCase())) {
            result.add(0, "DEAD");
            result.add(1, input.toUpperCase());
        } else {
            Game.caterpillar.setLastAction("You are Dead, you can only type restart/quit");
            throw new DeadPlayerInputException();

        }
        return result;
    }

    private String theOtherString(String string) {
        String result = inputArray[0];
        if (result.equals(string)) {
            result = inputArray[1];
        }
        return result.toUpperCase();
    }

    private void populateVerbs() {
        verbs = new HashSet<String>(Arrays.asList("GO", "EAT", "ATTACK", "CHEAT", "RECON"));
    }

    private void populateDirection() {
        directions = new HashSet<String>(Arrays.asList("NORTH", "SOUTH", "EAST", "WEST"));
    }

    private void populateCheatCode() {
        cheatCode = new HashSet<String>(Arrays.asList("STRENGTH", "HEALTH", "LEVEL", "AMAZON"));
    }

    private void populateItem() {
        items = new HashSet<String>();
        for (String str : Game.caterpillar.getCurrentLocation().getItems().keySet()) {
            items.add(str.toUpperCase());
        }

    }

    private void populateEnemy() {
        enemies = new HashSet<String>();
        for (Map.Entry<String, Enemy> entry : Game.caterpillar.getCurrentLocation().getEnemies().entrySet()) {
            if (!entry.getValue().isDead())
                enemies.add(entry.getKey().toUpperCase());
        }
        if (Game.caterpillar.getCurrentLocation().isBossPresent()) {
            enemies.add(Game.boss.getName().toUpperCase());
        }
    }

    private void populateDeadComand() {
        deadComand = new HashSet<String>(Arrays.asList("RESTART", "QUIT"));
    }
}
