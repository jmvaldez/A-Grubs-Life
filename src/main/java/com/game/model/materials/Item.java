package com.game.model.materials;

public class Item {

    private String name;
    private int health;
    private int exp;
    private int rarity;
    private int qty;
    private boolean isActive;

    public Item(String name, int health, int exp, int rarity){
        this.name = name;
        this.health = health;
        this.exp = exp;
        this.rarity = rarity;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getExp() {
        return exp;
    }

    public int getRarity() {
        return rarity;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}