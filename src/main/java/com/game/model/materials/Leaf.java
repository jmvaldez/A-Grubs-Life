package com.game.model.materials;

public class Leaf {
    private int xp;

    public Leaf() {
        this.xp = (int)(Math.random() * 4)+1;
    }

    public int getXp() {
        return xp;
    }
//    public int getXp() {
//        int xp = (int) (Math.random() * 5)+1;
//        return xp;
}