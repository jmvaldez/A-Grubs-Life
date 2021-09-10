package com.game.model.materials;

public class Leaf {
    private int xp;

    public Leaf() {
        this.xp = (int)(Math.random() * 4)+1;
    }

    public int getXp() {
        return xp;
    }
}