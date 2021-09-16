package com.game.model.engine;

import com.game.controller.Game;
import com.game.model.materials.Enemy;
import com.game.model.materials.Item;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Functions {

    public static ImageIcon readImage(String name) {
        ImageIcon image = null;
        try {
            image = new ImageIcon(Functions.class.getResource("/image/" + name + ".png"));
        }
        catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return image;
    }

    public static HashMap<String, Enemy> getRandomEnemies() {
        ArrayList<String> keyList = new ArrayList<String>(Game.getEnemies().keySet());
        int enemyQty = getRandomNumber(1, 3);
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
                    break;
                }
            }
        }
        return result;
    }

    public static HashMap<String, Item> getRandomItems() {
        ArrayList<String> keyList = new ArrayList<String>(Game.getItems().keySet());

        int itemQty = getRandomNumber(1, 2);

        ArrayList<Integer> usedIndex = new ArrayList<Integer>();
        HashMap<String, Item> result = new HashMap<>();
        for (int i = 0; i <= itemQty; i++) {
            while (true) {
                int itemAmount = getRandomNumber(1, 5);
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

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

}
