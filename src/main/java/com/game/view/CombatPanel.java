package com.game.view;

import com.game.controller.Game;
import com.game.model.engine.Functions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CombatPanel extends JPanel implements ActionListener {

    private JPanel imagePanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    private JLabel backgroundImage = new JLabel();
    private JLabel caterpillerImage = new JLabel();
    private JLabel enemyImage = new JLabel();
    private JLabel caterpillerHPbackImage = new JLabel();
    private JLabel enemyHPbackImage = new JLabel();
    private JLabel[] currentCaterpillarHPImage = new JLabel[10];
    private JLabel[] currenEnemyHPImage = new JLabel[10];
    private JButton attack = new JButton("Attack");
    private JButton defense = new JButton("Defense");
    private JButton run = new JButton("Run");
    private JButton mute = new JButton("Mute");

    public void setUpCombatPanel() {

        this.setLayout(null);
        this.setFocusable(true);
        this.setBackground(new Color(0, 0, 0));

        setImagePanel();

        this.add(imagePanel);


    }

    private void setImagePanel() {
        imagePanel.setLayout(null);
        imagePanel.setBounds(250, 150, 540, 325);
//        TitledBorder imageBorder = new TitledBorder("Combat");
//        imageBorder.setTitleColor(Color.GREEN);
//        imageBorder.setBorder(imageBorder);
        backgroundImage.setBounds(0, 0, 540, 325);
        backgroundImage.setIcon(Functions.readImage("combatBackground"));


        caterpillerImage.setBounds(50, 100, 100, 100);
        caterpillerImage.setIcon(Game.caterpillar.getCaterpillarImageIcon());

        enemyImage.setBounds(200, 100, 80, 100);
        enemyImage.setIcon(Game.caterpillar.engagedEnemy.getEnemyImageIcon());

        imagePanel.add(enemyImage);
        imagePanel.add(caterpillerImage);
        imagePanel.add(backgroundImage);


    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
