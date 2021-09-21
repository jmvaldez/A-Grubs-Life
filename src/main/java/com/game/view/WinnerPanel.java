package com.game.view;

import com.game.controller.Game;
import com.game.model.engine.Functions;
import com.game.util.GameAudio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class WinnerPanel extends JPanel implements KeyListener {

    private JLabel image = new JLabel();
    private JLabel text = new JLabel();
    private JLabel next = new JLabel();


    public void setWinnerPanel() {

        GameAudio.playAudio("winner");
        addKeyListener(this);
        this.setLayout(null);
        this.setFocusable(true);
        this.setBackground(new Color(0, 0, 0));
        this.add(image);
        this.add(text);


        image.setBounds(250, 150, 540, 325);
        image.setIcon(Functions.readImage("winner"));
        text.setBounds(300, 450, 600, 200);
        text.setForeground(Color.white);
        text.setFont(new Font("SANS_SERIF", Font.PLAIN, 20));
        text.setText("<html>Congrats!" +
                "<br/>you kill the Bird and save your forest!" +
                "<br/>Press 'ENTER' to continue, 'ESC' to leave.." +
                "</html>");
        Game.window.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                Game.initWelcomePanel();
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
