package com.game.view;

import com.game.controller.Game;

import javax.swing.*;
import java.awt.*;

public class HelpWindow {

    private static JLabel text = new JLabel();

    public static void createHelpWindow() {
        Game.helpWindow.setLayout(new BorderLayout());
        Game.helpWindow.setPreferredSize(new Dimension(500, 400));
        Game.helpWindow.setVisible(true);
        Game.helpWindow.setResizable(false);
        Game.helpWindow.setFocusable(true);
//        this.window.setLocationRelativeTo(null);
        Game.helpWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Game.helpWindow.pack();

        Game.helpWindow.setBackground(Color.black);
        Game.helpWindow.add(text);

        text.setFont(new Font("SANS_SERIF", Font.PLAIN, 20));
        text.setText("<html>" +
                "<h1>Available Commands: <h1>" +
                "<br>Go" +
                "<br>Attack" +
                "<br>Recon" +
                "<br>Eat" +
                "<br>Cheat" +
                "</html>");
    }

}

