package com.game.view;

import com.fasterxml.jackson.databind.JsonNode;
import com.game.controller.Game;
import com.game.model.engine.Functions;
import com.game.model.engine.JsonReader;
import com.game.util.GameAudio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

public class GameStoryPanel extends JPanel implements KeyListener {

    int storyCounter = 0;
    private ArrayList<ArrayList<String>> storyResourceMatrix = new ArrayList<ArrayList<String>>();
    private JLabel image = new JLabel();
    private JLabel text = new JLabel();
    private JLabel next = new JLabel();


    public void setUpGameStoryPanel() {

        setResource();
        addKeyListener(this);
        this.setLayout(null);
        this.setFocusable(true);
        this.setBackground(new Color(0, 0, 0));
        this.add(image);
        this.add(text);
        this.add(next);

        image.setBounds(250, 150, 540, 325);
        text.setBounds(300, 500, 600, 50);
        text.setForeground(Color.white);
        text.setFont(new Font("SANS_SERIF", Font.PLAIN, 30));
        next.setBounds(700, 650, 600, 50);
        next.setForeground(Color.white);
        next.setFont(new Font("SANS_SERIF", Font.PLAIN, 20));
        next.setText("Press 'ENTER' to continue...");
        loadStoryPanel(storyCounter);
        Game.window.repaint();
    }

    private void loadStoryPanel(int number) {
        image.setIcon(Functions.readImage(storyResourceMatrix.get(number).get(0)));
        text.setText(storyResourceMatrix.get(number).get(1));
    }

    private void setResource() {
        try {
            String locationsStream = JsonReader.getJsonStream("/json/GameStory.json");
            JsonNode node = JsonReader.parse(locationsStream);

            Iterator<Map.Entry<String, JsonNode>> nodes = node.get("GameStory").fields();

            while (nodes.hasNext()) {
                Map.Entry<String, JsonNode> entry = nodes.next();

                String name = entry.getKey();
                String story = entry.getValue().get("story").asText();
                storyResourceMatrix.add(new ArrayList<>(Arrays.asList(name, story)));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                GameAudio.playAudio("Next");
                storyCounter++;
                if (storyCounter < storyResourceMatrix.size() - 1) {
                    loadStoryPanel(storyCounter);
                } else if (storyCounter < storyResourceMatrix.size()) {
                    loadStoryPanel(storyCounter);
                    next.setText("Press Enter to Start the Game!...");
                } else {
                    GameAudio.PlayWelcomeAudio();
                    this.setFocusable(false);
                    Game.initGame();
                }
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
