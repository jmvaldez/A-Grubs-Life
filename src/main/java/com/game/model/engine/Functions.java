package com.game.model.engine;

import com.game.controller.Game;
import com.game.model.materials.Enemy;
import com.game.model.materials.Item;
import com.game.util.GameAudio;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Functions {


    public static ImageIcon readImage(String name) {
        ImageIcon image = null;
        try {
            image = new ImageIcon(Functions.class.getResource(Game.IMAGE_PATH + name + ".png"));
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return image;
    }

    public static HashMap<String, Enemy> getRandomEnemies() {
        ArrayList<String> keyList = new ArrayList<String>(Game.getEnemies().keySet());
        int enemyQty = getRandomNumber(Game.LOCATION_ENEMY_MIN_QTY, Game.LOCATION_ENEMY_MAX_QTY);
        ArrayList<Integer> usedIndex = new ArrayList<Integer>();
        HashMap<String, Enemy> result = new HashMap<>();
        for (int i = 0; i <= enemyQty; i++) {
            while (true) {
                int index = getRandomNumber(0, Game.getEnemies().size());
                if (!usedIndex.contains(index)) {
                    usedIndex.add(index);
                    String name = keyList.get(index);
                    result.put(name, Game.getEnemies().get(name));
                    result.get(name).setHealth(result.get(name).getMaxHealth());
                    result.get(name).setAlive();
                    break;
                }
            }
        }
        return result;
    }

    public static HashMap<String, Item> getRandomItems() {
        ArrayList<String> keyList = new ArrayList<String>(Game.getItems().keySet());

        int itemQty = getRandomNumber(Game.LOCATION_ITEM_MIN_QTY, Game.LOCATION_ITEM_MAX_QTY);

        ArrayList<Integer> usedIndex = new ArrayList<Integer>();
        HashMap<String, Item> result = new HashMap<>();
        for (int i = 0; i <= itemQty; i++) {
            while (true) {
                int itemAmount = getRandomNumber(Game.ITEM_MIN_QTY, Game.ITEM_MAX_QTY);
                int index = getRandomNumber(0, Game.getItems().size());
                if (!usedIndex.contains(index)) {
                    usedIndex.add(index);
                    String name = keyList.get(index);
                    result.put(name, Game.getItems().get(name));
                    result.get(name).setQty(itemAmount);
                    break;
                }
            }
        }
        return result;
    }

    public static void setCurrentLocationElement(String location) {

        switch (location) {
            case "DEAD_END":
                Game.caterpillar.setLastAction("Dead End, You're trapped! Go another direction.");
                GameAudio.playAudio("Leave");
                break;
            case "Genesis":

                Game.caterpillar.setCurrentLocation("Genesis");
                GameAudio.playAudio("Move");
                Game.caterpillar.getCurrentLocation().setItems(Functions.getRandomItems());
                Game.caterpillar.getCurrentLocation().setEnemies(new HashMap<>());
                break;
            default:
                GameAudio.playAudio("Move");
                Game.caterpillar.setCurrentLocation(location);
                Game.caterpillar.getCurrentLocation().setItems(Functions.getRandomItems());
                Game.caterpillar.getCurrentLocation().setEnemies(Functions.getRandomEnemies());
        }

    }

    // method for random chance to execute an action.
    public static boolean chanceForAction(int min, int max, int chance) {
        int rand = Functions.getRandomNumber(min, max);
        return rand > chance;
    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

}
