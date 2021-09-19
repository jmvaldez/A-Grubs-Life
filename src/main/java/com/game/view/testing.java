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

public class testing extends JPanel implements KeyListener {

    private int x = 0;
    private int y = 0;
    private int a = 0;
    private int b = 0;

    public testing() {
        addKeyListener(this);
        this.setFocusable(true);

        Timer timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveBall();
                repaint();
            }
        });
        timer.start();

        Timer welcomeInstructionTimer = new Timer(5000, e -> {
            setWelcomeInstruction();
            repaint();
        });
        welcomeInstructionTimer.start();
    }

    private void setWelcomeInstruction(){
        JLabel welcomeStartNote = new JLabel();
        welcomeStartNote.setBounds(350, 620, 600, 50);
        welcomeStartNote.setForeground(Color.black);
        welcomeStartNote.setFont(new Font("SANS_SERIF", Font.PLAIN, 30));
        welcomeStartNote.setText("Press 'ENTER' to start...");
        this.add(welcomeStartNote);
    }

    protected void moveBall() {
        if (x <= 300) {
            x += 10;
            y += 10;
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500, 500);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Functions.readImage("welcomeLogo").paintIcon(this, g, x,y);
        g.dispose();

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.RED);
        g2d.fillOval(x, y, 30, 30);
        g2d.dispose();
    }


    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                GameAudio.PlayWelcomeAudio();
//                this.setFocusable(false);
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

    @Override
    public void keyTyped(KeyEvent e) {

    }
}




