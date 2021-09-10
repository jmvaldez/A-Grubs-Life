package com.game.model.materials;

public class Combat {
    private Caterpillar caterpillar;
    private Enemy enemy;

    public Combat(Caterpillar caterpillar, Enemy enemy){
        this.caterpillar = caterpillar;
        this.enemy = enemy;
    }

    public void printFightStart(Enemy enemy){
        System.out.printf("\nYou have decided to engage the Enemy:%s", enemy.getName());
        System.out.printf("\nYour enemy has %d strength and %d health", enemy.getStrength(), enemy.getHealth());
    }
    public void printCaterpillarFightStats(Caterpillar caterpillar){
        System.out.printf("\nYou have %d health", caterpillar.getHealth());
    }
    public void printEnemyFightStats(Enemy enemy) {
        System.out.printf("\nEnemy %s has %d health", enemy.getName(), enemy.getHealth());
    }

}
