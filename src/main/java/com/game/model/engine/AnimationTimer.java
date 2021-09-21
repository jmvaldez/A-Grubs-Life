package com.game.model.engine;

import com.game.controller.Game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TimerTask;

public class AnimationTimer {

    public static Timer healthIncreaseAnimationTimer;
    public static Timer bossHarassTimer;


    public AnimationTimer() {
        setHealthAnimationTimer();
        setBossHarassTimer();
    }

    public static void startActionImageTimer(int delayTime) {

        TimerTask startAttackAnimation = new TimerTask() {
            @Override
            public void run() {
                Game.getGamePanel().actionAnimationLabel.setIcon(null);
                Game.getGamePanel().actionAnimationLabel.repaint();
            }
        };
        new java.util.Timer().schedule(startAttackAnimation, delayTime);
    }

    private void setBossHarassTimer() {

        bossHarassTimer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setHarassAction();
                Game.getGamePanel().updateLabels();

            }

            int birdHarass = 3;

            private void setHarassAction() {
                if (Game.caterpillar.getCurrentLocation().isBossPresent() && !Game.caterpillar.isDead()) {
                    Game.caterpillar.setHealth(Game.caterpillar.getHealth() - birdHarass);
                    Game.caterpillar.setLastAction("Bird took your " + birdHarass + " point health, you have " + Game.caterpillar.getHealth() + " point health left, better RUN NOW!!!");
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
                Game.getGamePanel().healthIncreaImageLabel.repaint();
            }

            private void moveIncreaseHealthLabel() {
                if (startYpos >= 100) {
                    startYpos -= 5;
                    Game.getGamePanel().healthIncreaImageLabel.setBounds(280, startYpos, 30, 25);
                    Game.getGamePanel().healthIncreaImageLabel.setIcon(Functions.readImage("firstAid"));
                } else {
                    Game.getGamePanel().healthIncreaImageLabel.setIcon(null);
                    Game.getGamePanel().repaint();
                    startYpos = 200;
                    healthIncreaseAnimationTimer.stop();
                }
            }
        });
    }


}
