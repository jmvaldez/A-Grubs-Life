package com.game.view;

import com.game.controller.Game;

import javax.swing.*;
import java.awt.*;

public class HelpWindow {

    public static void creatHelpWindow() {
        Game.helpWindow.setLayout(new BorderLayout());
        Game.helpWindow.setPreferredSize(new Dimension(500, 400));
        Game.helpWindow.setVisible(true);
        Game.helpWindow.setResizable(false);
        Game.helpWindow.setFocusable(true);
//        this.window.setLocationRelativeTo(null);
        Game.helpWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Game.helpWindow.pack();
    }
}

