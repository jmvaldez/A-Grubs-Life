package com.game.model.materials;

public class Caterpillar {
    public boolean winner;
    private int health;
    private int experience;
    private int strength ;
    private int level = 1;
    private int maxLevel = 3;
    private int maxExperience = 5;

    private Location currentLocation;
    private boolean hidden;
    private String lastAction;

    public  Caterpillar(int health, int experience, int strength){
        this.health = health;
        this.experience = experience;
        this.strength = strength;
        this.hidden = false;
        this.lastAction = "";
        this.winner = false;
    }
    public void setCurrentLocation(Location location){ //we should move this to the bottom
        this.currentLocation = location;
    }
    public Location getCurrentLocation(){
        return this.currentLocation;
    }
    public void eat(Leaf leaf){
        setHealth(getHealth() + 10);
        if( (getExperience() + leaf.getXp()) >= maxExperience && level < maxLevel) {
            setExperience((getExperience() + leaf.getXp()) % maxExperience); //level up and transfers remaining to experience
            levelUp(); //increases level / ends the stage once appropriate level
            }
        else{
            if(level < maxLevel){
                setExperience(getExperience() + leaf.getXp() ); // no levelup by experience up
            }

        }
    }
    public void levelUp(){
        setStrength(strength + 50);
        setLevel(level + 1);
        if(getLevel() == maxLevel- 1){
            this.setLastAction("You are level 2! You feel slightly stronger and more healthy.");
        }
        else if(getLevel()== maxLevel) {
            this.setLastAction("You have reached level 3! You are now a butterfly... from now on you can use acid attacks.");
            }

    }

    public void healthRegenerator(int counter){
        if(counter % 2934342 == 0){
            setHealth(getHealth() + 1);
        }
    }
    public int getHealth() {
        return health;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setLastAction(String str){
        this.lastAction = str;
    }
    public String getLastAction(){
        return this.lastAction;
    }


    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
    public int getMaxExperience() {
        return maxExperience;
    }
}
