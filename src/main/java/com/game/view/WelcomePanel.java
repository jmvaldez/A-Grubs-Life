package com.game.view;

import com.game.controller.Game;
import com.game.model.engine.Functions;
import com.game.util.GameAudio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class WelcomePanel extends JPanel implements KeyListener {


    Timer welcomeImageTimer;
    Timer welcomeLogoTimer;
    Timer welcomeLogoDelayTimer;
    Timer welcomeInstructionDelayTimer;


    private JLabel welcomeImage = new JLabel();
    private JLabel welcomeLogo = new JLabel();
    private JLabel welcomeStartNote = new JLabel();

    private int welcomeImageStartXpos = 1024;
    private int welcomeImageStartYpos = 230;
    private int welcomeLogoStartXpos = 180;
    private int welcomeLogoStartYpos = 100;
    private int welcomeCounter = 0;


    public WelcomePanel() {

        addKeyListener(this);
        this.setLayout(null);
        this.setFocusable(true);
        this.setBackground(new Color(0, 0, 0));
        this.add(welcomeImage);
        this.add(welcomeLogo);
        this.add(welcomeStartNote);

        welcomeImageTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setWelcomeImage();
                animationWelcomeImage();
                repaint();
            }
        });


        welcomeLogoDelayTimer = new Timer(2500, e -> {
            welcomeLogoTimer = new Timer(50, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setWelcomeLogo();
                    animationWelcomeLogo();
                    repaint();
                }
            });
            welcomeLogoTimer.start();
        });


        welcomeInstructionDelayTimer = new Timer(5500, e -> {
            setWelcomeInstruction();
            repaint();
        });

        welcomeImageTimer.start();
        welcomeLogoDelayTimer.start();
        welcomeInstructionDelayTimer.start();

    }

    private void animationWelcomeImage() {
        if (welcomeImageStartXpos >= 410) {
            welcomeImageStartXpos -= 4;
        } else {
            welcomeImageTimer.stop();
        }
    }

    private void setWelcomeImage() {

        welcomeImage.setBounds(welcomeImageStartXpos, welcomeImageStartYpos, 500, 300);
        welcomeImage.setIcon(Functions.readImage("welcomeImage"));

    }

    private void animationWelcomeLogo() {
        if (6 < welcomeCounter && welcomeCounter < 16) {
            switch (welcomeCounter % 6) {
                case 0:
                    welcomeLogo.setIcon(Functions.readImage("welcomeLogo"));
                    break;
                case 4:
                    welcomeLogo.setIcon(null);
                    break;
            }
        } else if (16 <= welcomeCounter && welcomeCounter <= 24) {
            switch (welcomeCounter % 3) {
                case 0:
                case 1:
                    welcomeLogo.setIcon(Functions.readImage("welcomeLogo"));
                    break;
                case 2:
                    welcomeLogo.setIcon(null);
                    break;
            }
        }
        welcomeCounter++;
    }

    private void setWelcomeLogo() {
        welcomeLogo.setBounds(welcomeLogoStartXpos, welcomeLogoStartYpos, 600, 250);
        welcomeLogo.setIcon(Functions.readImage("welcomeLogo"));
        welcomeLogo.repaint();
    }

    private void setWelcomeInstruction() {

        welcomeStartNote.setBounds(350, 620, 600, 50);
        welcomeStartNote.setForeground(Color.white);
        welcomeStartNote.setFont(new Font("SANS_SERIF", Font.PLAIN, 30));
        welcomeStartNote.setText("Press 'ENTER' to continue...");


        welcomeInstructionDelayTimer.stop();
        welcomeLogoTimer.stop();
        welcomeLogoDelayTimer.stop();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                this.setFocusable(false);
                GameAudio.playAudio("Next");
                Game.initGameStoryPanel();
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
            default:
                GameAudio.playAudio("ICANT");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
