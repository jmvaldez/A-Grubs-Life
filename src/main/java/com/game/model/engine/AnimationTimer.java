package com.game.model.engine;

import com.game.controller.Game;
import com.game.model.materials.Enemy;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnimationTimer {

    public static Timer healthIncreaseAnimationTimer;
    public static Timer cheatAmazonTimer;

    public AnimationTimer(){
        setHealthAnimationTimer();
        setAttackAnimationTimer();
        setCheatAmazonTimer();

    }

    private void setHealthAnimationTimer() {

        healthIncreaseAnimationTimer = new Timer(40, new ActionListener() {
            int startYpos = 200;
            @Override
            public void actionPerformed(ActionEvent e) {
                moveIncreaseHealthLabel();
                Game.gamePanel.healthIncreaseLabel.repaint();
            }

            private void moveIncreaseHealthLabel() {
                if (startYpos >= 100) {
                    startYpos -= 5;
                    Game.gamePanel.healthIncreaseLabel.setBounds(280, startYpos, 30, 25);
                    Game.gamePanel.healthIncreaseLabel.setIcon(Functions.readImage("firstAid"));
                }else{
                    Game.gamePanel.healthIncreaseLabel.setIcon(null);
                    Game.gamePanel.repaint();
                    startYpos = 200;
                    healthIncreaseAnimationTimer.stop();
                }
            }
        });
    }

    //TODO:

    private void setAttackAnimationTimer(){

    }

    private void setCheatAmazonTimer(){

        cheatAmazonTimer = new Timer(3000, e -> {
            Game.gamePanel.cheatImageLabel.setIcon(null);
            Game.gamePanel.cheatImageLabel.repaint();
            cheatAmazonTimer.stop();
        });

    }


}
