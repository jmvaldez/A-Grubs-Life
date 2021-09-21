package com.game.model.engine;

import com.game.controller.Game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnimationTimer {

    public static Timer healthIncreaseAnimationTimer;
    public static Timer cheatAmazonTimer;
    public static Timer bossHarassTimer;

    public AnimationTimer() {
        setHealthAnimationTimer();
        setAttackAnimationTimer();
        setCheatAmazonTimer();
        setBossHarassTimer();

    }

    private void setBossHarassTimer() {

        bossHarassTimer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setHarassAction();
                Game.getGamePanel().updateLabels();

            }

            private void setHarassAction() {
                if (Game.caterpillar.getCurrentLocation().isBossPresent() && !Game.caterpillar.isDead()) {
                    Game.caterpillar.setHealth(Game.caterpillar.getHealth() - 1);
                    Game.caterpillar.setLastAction("Bird took your 1 point health, you have "+ Game.caterpillar.getHealth() + " point health left, better RUN NOW!!!");
                    Game.caterpillar.checkDeath();

                } else {
                    bossHarassTimer.stop();
                }
            }
        });
    }

    private void setHealthAnimationTimer() {

        healthIncreaseAnimationTimer = new Timer(40, new ActionListener() {
            int startYpos = 200;

            @Override
            public void actionPerformed(ActionEvent e) {
                moveIncreaseHealthLabel();
                Game.getGamePanel().healthIncreaseLabel.repaint();
            }

            private void moveIncreaseHealthLabel() {
                if (startYpos >= 100) {
                    startYpos -= 5;
                    Game.getGamePanel().healthIncreaseLabel.setBounds(280, startYpos, 30, 25);
                    Game.getGamePanel().healthIncreaseLabel.setIcon(Functions.readImage("firstAid"));
                } else {
                    Game.getGamePanel().healthIncreaseLabel.setIcon(null);
                    Game.getGamePanel().repaint();
                    startYpos = 200;
                    healthIncreaseAnimationTimer.stop();
                }
            }
        });
    }

    //TODO:

    private void setAttackAnimationTimer() {

    }

    private void setCheatAmazonTimer() {

        cheatAmazonTimer = new Timer(3000, e -> {
            Game.getGamePanel().cheatImageLabel.setIcon(null);
            Game.getGamePanel().cheatImageLabel.repaint();
            cheatAmazonTimer.stop();
        });

    }


}
