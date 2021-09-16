package com.game.model.engine;

import com.game.controller.Game;
import com.game.exception.DeadPlayerInputException;
import com.game.exception.InputLengthException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;

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
        populateItem();
        populateEnemy();
        populateCheatCode();
        populateDeadComand();

    }

    //If we dont get a viable verb and noun then we will pass null.
    public ArrayList<String> parserLiveInput(String input) {
        inputArray = input.toUpperCase(Locale.ROOT).split(" ");
        if (inputArray.length != 2) {
            Game.caterpillar.setLastAction("TWO words Command ONLY!");
            throw new InputLengthException();
        }

        ArrayList<String> result = new ArrayList<>();

        for (String str : inputArray) {
            String otherStr = theOtherString(str);

            switch (str.toUpperCase()) {
                case "GO":
                    if (directions.contains(otherStr) && !str.equalsIgnoreCase(otherStr)) {
                        result.add(0, str);
                        result.add(1, otherStr);
                    } else {
                        Game.caterpillar.setLastAction("You can not go " + otherStr + "!! Go North/South/East/West wherever is showing on the MAP!");
                    }
                    break;
                case "EAT":
                    if (items.contains(otherStr) && !str.equalsIgnoreCase(otherStr)) {
                        result.add(0, str);
                        result.add(1, otherStr);
                    } else {
                        Game.caterpillar.setLastAction("Can you eat " + otherStr + "?! Check the item list!");
                    }
                    break;
                case "ATTACK":
                    if (enemies.contains(otherStr) && !str.equalsIgnoreCase(otherStr)) {
                        result.add(0, str);
                        result.add(1, otherStr);
                    } else {
                        Game.caterpillar.setLastAction("Is " + otherStr + " a lived Enemy?! Check the enemy list!");
                    }
                    break;
                case "CHEAT":

                    if (cheatCode.contains(otherStr) && !str.equalsIgnoreCase(otherStr)) {
                        result.add(0, str);
                        result.add(1, otherStr);
                    } else {
                        Game.caterpillar.setLastAction("Nice Try, Your Cheating Code is not Valid");
                    }
                    break;
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
        verbs = new HashSet<String>(Arrays.asList("GO", "EAT", "ATTACK", "CHEAT"));
    }

    private void populateDirection() {
        directions = new HashSet<String>(Arrays.asList("NORTH", "SOUTH", "EAST", "WEST"));
    }

    private void populateCheatCode() {
        cheatCode = new HashSet<String>(Arrays.asList("XP", "HEALTH", "LEVEL", "EXP"));
    }

    private void populateItem() {
        items = new HashSet<String>();
        for (String str : Game.getItems().keySet()) {
            items.add(str.toUpperCase());
        }
        for (String str: items){
            System.out.println(str);
        }

    }

    private void populateEnemy() {
        enemies = new HashSet<String>();
        for (String str : Game.getEnemies().keySet()) {
            enemies.add(str.toUpperCase());
        }
    }

    private void populateDeadComand() {
        deadComand = new HashSet<String>(Arrays.asList("RESTART", "QUIT"));
    }
}
