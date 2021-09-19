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

    JLabel welcomeImage = new JLabel();
    private int welcomeImageStartXpos = 1024;
    private int welcomeImageStartYpos = 230;

    public WelcomePanel() {

        addKeyListener(this);
        this.setFocusable(true);
        this.setBackground(new Color(0, 0, 0));

        Timer welcomeImageTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveWelcomeImage();
                repaint();
            }
        });


        Timer welcomeLogoTimer = new Timer(2500, e -> {
            setWelcomeLogo();
            repaint();
        });


        Timer welcomeInstructionTimer = new Timer(5000, e -> {
            setWelcomeInstruction();
            repaint();
        });

        welcomeImageTimer.start();
        welcomeLogoTimer.start();
        welcomeInstructionTimer.start();
    }

    protected void moveWelcomeImage() {
        if (welcomeImageStartXpos >= 410) {
            welcomeImageStartXpos -= 5;
        }
    }

    private void setWelcomeLogo() {

        JLabel welcomeLogo = new JLabel();
        welcomeLogo.setBounds(180, 100, 600, 250);
        welcomeLogo.setIcon(Functions.readImage("welcomeLogo"));
        this.add(welcomeLogo);

    }

    private void setWelcomeInstruction() {

        JLabel welcomeStartNote = new JLabel();

        welcomeStartNote.setBounds(350, 620, 600, 50);

        welcomeStartNote.setForeground(Color.white);
        welcomeStartNote.setFont(new Font("SANS_SERIF", Font.PLAIN, 30));
        welcomeStartNote.setText("Press 'ENTER' to start...");

        this.add(welcomeStartNote);
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Functions.readImage("welcomeImage").paintIcon(this, g, welcomeImageStartXpos, welcomeImageStartYpos);
//        g.dispose();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                GameAudio.PlayWelcomeAudio();
                this.setFocusable(false);
                Game.initGame();
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
