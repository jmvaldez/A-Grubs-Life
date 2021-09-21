package com.game.view;

import com.game.controller.Game;
import com.game.model.engine.Functions;
import com.game.model.materials.Enemy;
import com.game.model.materials.Item;
import com.game.model.materials.Location;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GamePanel extends JPanel {

    public JLabel healthIncreaImageLabel;
    public JLabel actionImageLabel;
    public JLabel actionAnimationLabel;

    public JLabel lastMoveLabel;
    private JLabel caterpillarStatLabel;
    private JLabel enemyStatLabel;
    private JLabel descriptionLabel;
    private JLabel enemyListLabel;
    private JLabel itemListLabel;
    private JTextField inputField;
    private JPanel inputPanel;
    private JPanel statPanel;
    private JPanel locationPanel;
    private JLabel mapLabel;
    private JLabel northRoomLabel;
    private JLabel southRoomLabel;
    private JLabel eastRoomLabel;
    private JLabel westRoomLabel;
    private JLabel currentRoomLabel;
    private JLabel northEmptyLabel;
    private JLabel westEmptyLabel;
    private JLabel northEastLabel;
    private JLabel roomImageLabel;
    public JLabel caterpillarImageLabel;
    private JLabel backgroundLabel;

    private JLabel bossImageLabel;
    private JLabel[] bossHPLabelList;
    private JLabel[] itemLabelList;
    private JLabel[] enemyLabelList;
    private JLabel[] itemQtyLabelList;
    private JLabel[] enemyHPLabelList;
    private JLabel[] loserLabelList;

    /////////////FIELDS AUDIO FUNCTION///////////
    private JButton soundButton;
    private String rickRoll;
    private String musicOnOff;
    private Music mu;
    private JSlider slider;
    private JPanel soundPanel;
    private float val;
    private javax.swing.event.ChangeListener ChangeListener;
    /////////////////////////////////////////////


    public void setUpGamePanel() {
        setSoundPanel();
        setUpInputPanel();
        setUpDescriptionPanel();
        setUpLocationPanel();
        setUpStatPanel();

    }

    private void setUpInputPanel() {
        inputPanel = new JPanel();
        this.lastMoveLabel = new JLabel();
        this.inputField = new JTextField(50);
        Color background = new Color(10, 80, 20, 158);
        inputPanel.setLayout(new BorderLayout());
        inputPanel.setBorder(BorderFactory.createLineBorder(new Color(110, 16, 5)));
        inputPanel.setBackground(background);
        inputPanel.setPreferredSize(new Dimension(1000, 200));
        lastMoveLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        lastMoveLabel.setPreferredSize(new Dimension(1024, 150));
        setUpInputField(inputPanel);
        setUpLastMoveLabel();
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(lastMoveLabel, BorderLayout.NORTH);
        inputPanel.add(soundPanel, BorderLayout.EAST);
        Game.window.add(inputPanel, BorderLayout.SOUTH);
    }

    private void setUpInputField(JPanel inputPanel) {

        inputField.setBorder(BorderFactory.createTitledBorder("Enter your command as a [VERB/NOUN]: \n " +
                ""));
        inputField.setBackground(new Color(217, 224, 214));
        inputField.addActionListener(e -> {

            String input = inputField.getText();
            Game.getProcessor().processUserInput(input);
            inputField.setText("");
            updateLabels();

        });
    }

    private void setUpLastMoveLabel() {

        String lastAction = Game.caterpillar.getLastAction();
        lastMoveLabel.setBorder(BorderFactory.createTitledBorder("Note"));

        lastMoveLabel.setText("<html> " +
                "<h3>" + lastAction + "</h3>" +
                "</html>");
    }

    private void setUpStatPanel() {
        JPanel statPanel = new JPanel();
        this.caterpillarStatLabel = new JLabel();
        this.enemyStatLabel = new JLabel();
        statPanel.setLayout(new BorderLayout());
        statPanel.setBackground(new Color(0, 0, 0));
        statPanel.setPreferredSize(new Dimension(230, 600));
        setCaterpillarStatLabel();
        setEnemyStatLabel();
        statPanel.add(caterpillarStatLabel, BorderLayout.NORTH);
        statPanel.add(enemyStatLabel, BorderLayout.SOUTH);

        Game.window.add(statPanel, BorderLayout.EAST);


    }

    private void setUpLocationPanel() {

        JPanel locationPanel = new JPanel();

        northRoomLabel = new JLabel();
        southRoomLabel = new JLabel();
        eastRoomLabel = new JLabel();
        westRoomLabel = new JLabel();
        currentRoomLabel = new JLabel();
        northEastLabel = new JLabel();
        mapLabel = new JLabel();

        locationPanel.setLayout(new BorderLayout());
        locationPanel.setBackground(new Color(0, 0, 0));
        locationPanel.setPreferredSize(new Dimension(230, 600));


        setMapPanel(locationPanel);
        setRoomPanel(locationPanel);

        Game.window.add(locationPanel, BorderLayout.WEST);


    }

    private void setUpDescriptionPanel() {
        JPanel descriptionPanel = new JPanel(null);
        JPanel animationPanel = new JPanel(null);

        itemLabelList = new JLabel[Game.MAX_ENEMY_AND_ITEM_QTY_SETT_IN_ANIMATION_PANEL];
        itemQtyLabelList = new JLabel[Game.MAX_ENEMY_AND_ITEM_QTY_SETT_IN_ANIMATION_PANEL];
        enemyLabelList = new JLabel[Game.MAX_ENEMY_AND_ITEM_QTY_SETT_IN_ANIMATION_PANEL];
        enemyHPLabelList = new JLabel[Game.MAX_ENEMY_AND_ITEM_QTY_SETT_IN_ANIMATION_PANEL];
        loserLabelList = new JLabel[Game.MAX_ENEMY_AND_ITEM_QTY_SETT_IN_ANIMATION_PANEL];

        descriptionLabel = new JLabel();
        backgroundLabel = new JLabel();
        caterpillarImageLabel = new JLabel();
        healthIncreaImageLabel = new JLabel();
        actionImageLabel = new JLabel();
        actionAnimationLabel = new JLabel();
        bossImageLabel = new JLabel();
        bossHPLabelList = new JLabel[10];

        descriptionPanel.setBackground(new Color(0, 0, 0));
        animationPanel.setBackground(new Color(0, 0, 0));
        descriptionPanel.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255)));
        animationPanel.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255)));


        descriptionPanel.add(descriptionLabel);
        descriptionPanel.add(animationPanel);

        descriptionLabel.setBounds(0, 0, 550, 195);
        animationPanel.setBounds(0, 195, 550, 335);
        backgroundLabel.setBounds(5, 5, 540, 325);
        caterpillarImageLabel.setBounds(210, 190, 100, 100);
        actionImageLabel.setBounds(130, 130, 300, 130);
        actionAnimationLabel.setBounds(130, 30, 300, 280);
        bossImageLabel.setBounds(10, 10, 200, 160);


        animationPanel.add(actionAnimationLabel);
        animationPanel.add(actionImageLabel);
        animationPanel.add(healthIncreaImageLabel);


        for (int enemyHPImagePixel = 0; enemyHPImagePixel < 10; enemyHPImagePixel++) {
            bossHPLabelList[enemyHPImagePixel] = new JLabel();
            bossHPLabelList[enemyHPImagePixel].setBounds(10 + 20 * enemyHPImagePixel, 10, 20, 20);
            animationPanel.add(bossHPLabelList[enemyHPImagePixel]);

        }
        animationPanel.add(bossImageLabel);

        for (int i = 0; i < Game.MAX_ENEMY_AND_ITEM_QTY_SETT_IN_ANIMATION_PANEL; i++) {
            int itemLabelXpos = 140 - i * 60;
            int itemQtyXpos = 180 - i * 60;
            // enemyLabel x, y : 1, (120, 80) | 2, (360, 80) | 3,(240, 50)
            //240, 50     340, 80       460, 110
//            int enemyLabelXpos = 120 + 240 * i - 360 * ((i + 1) / 3);
//            int enemyLabelYpos = 80 - 30 * ((i + 1) / 3);
            int enemyLabelXpos = 460 - i * 100;
            int enemyLabelYpos = 110 - i * 30;


            itemLabelList[i] = new JLabel();
            itemQtyLabelList[i] = new JLabel();
            enemyLabelList[i] = new JLabel();
            enemyHPLabelList[i] = new JLabel();
            loserLabelList[i] = new JLabel();

            itemLabelList[i].setBounds(itemLabelXpos, 250, 50, 50);
            itemQtyLabelList[i].setBounds(itemQtyXpos, 290, 30, 30);
            enemyLabelList[i].setBounds(enemyLabelXpos, enemyLabelYpos, 80, 80);
            enemyHPLabelList[i].setBounds(enemyLabelXpos, enemyLabelYpos - 10, 80, 10);
            loserLabelList[i].setBounds(enemyLabelXpos, enemyLabelYpos, 80, 80);


            animationPanel.add(itemLabelList[i]);
            animationPanel.add(itemQtyLabelList[i]);
            animationPanel.add(loserLabelList[i]);
            animationPanel.add(enemyLabelList[i]);
            animationPanel.add(enemyHPLabelList[i]);

        }
        animationPanel.add(caterpillarImageLabel);
        animationPanel.add(backgroundLabel);

        Game.window.add(descriptionPanel, BorderLayout.CENTER);

        setDiscriptionLabel();
        setImageLabels();

    }

    private void setCaterpillarStatLabel() {

        caterpillarStatLabel.setText("<html>\n" +
                "<style>\n" +
                "table {\n" +
                "color:green;\n" +
                "font-size:15px;\n" +
                "padding:15px;\n" +
                "}\n" +
                "</style>\n" +
                "<table style=\"width:5%\">\n" +
                "<tr>\n" +
                "<td style=\"text-align: left;\">Strength: </td><td>" + Game.caterpillar.getStrength() +
                "</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td style=\"text-align: left;\">Health: </td><td>" + Game.caterpillar.getHealth() +
                "/" + Game.caterpillar.getLevelMaxHealth() +
                "</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td style=\"text-align: left;\">Level: </td><td>" + Game.caterpillar.getLevel() +
                "</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td style=\"text-align: left;\">Experience: </td><td>" + Game.caterpillar.getExperience() +
                "/" + Game.caterpillar.getLevelMaxExp() +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "\n" +
                "</html>");
        caterpillarStatLabel.setBorder(BorderFactory.createTitledBorder("Caterpillar"));

        TitledBorder tb = new TitledBorder("Caterpillar Stats");
        tb.setTitleColor(Color.GREEN);
        caterpillarStatLabel.setBorder(tb);
        caterpillarStatLabel.setPreferredSize(new Dimension(300, 315));

    }

    private void setEnemyStatLabel() {

        if (Game.caterpillar.engagedEnemy != null) {
            enemyStatLabel.setText(
                    "<html>\n" +
                            "<style>\n" +
                            "table {\n" +
                            "color:green;\n" +
                            "font-size:15px;\n" +
                            "padding:15px;\n" +
                            "}\n" +
                            "</style>\n" +
                            "<table style=\"width:5%\">\n" +
                            "<tr>\n" +
                            "<td style=\"text-align: left;\">Enemy: </td><td>" +
                            Game.caterpillar.engagedEnemy.getName() +
                            "</td>\n" +
                            "</tr>\n" +
                            "<tr>\n" +
                            "<td style=\"text-align: left;\">Strength: </td><td>" +
                            Game.caterpillar.engagedEnemy.getStrength() +
                            "</td>\n" +
                            "</tr>\n" +
                            "<tr>\n" +
                            "<td style=\"text-align: left;\">Health: </td><td>" +
                            Game.caterpillar.engagedEnemy.getHealth() +
                            "</td>\n" +
                            "</tr>\n" +
                            "</table>\n" +
                            "\n" +
                            "</html>");

        } else {
            enemyStatLabel.setText("");
        }

        TitledBorder eb = new TitledBorder("Target Status");
        eb.setTitleColor(Color.GREEN);
        enemyStatLabel.setBorder(eb);
        enemyStatLabel.setPreferredSize(new Dimension(300, 220));

    }

    private void setImageLabels() {
        setBossImageLabel();
        backgroundLabel.setIcon(Game.caterpillar.getCurrentLocation().getBackgroundImageIcon());
        caterpillarImageLabel.setIcon(Game.caterpillar.getCaterpillarImageIcon());
        actionImageLabel.setIcon(Game.caterpillar.getActionImageIcon());

        HashMap<String, Item> currentItems = new HashMap<>(Game.caterpillar.getCurrentLocation().getItems());
        HashMap<String, Enemy> currentEnemies = new HashMap<>(Game.caterpillar.getCurrentLocation().getEnemies());


        //TODO:why not string but Object
        Object[] itemKeyList = currentItems.keySet().toArray();
        Object[] enemyKeyList = currentEnemies.keySet().toArray();

        for (int i = 0; i < Game.MAX_ENEMY_AND_ITEM_QTY_SETT_IN_ANIMATION_PANEL; i++) {

            try {
                itemLabelList[i].setIcon(currentItems.get(itemKeyList[i]).getItemImageIcon());
                itemQtyLabelList[i].setForeground(Color.RED);
                itemQtyLabelList[i].setFont(new Font("Serif", Font.BOLD, 15));
                itemQtyLabelList[i].setText("x " + currentItems.get(itemKeyList[i]).getQty());
            } catch (IndexOutOfBoundsException exception) {
                itemLabelList[i].setIcon(null);
                itemQtyLabelList[i].setText(null);
            }
        }

        for (int i = 0; i < Game.MAX_ENEMY_AND_ITEM_QTY_SETT_IN_ANIMATION_PANEL; i++) {
            try {
                enemyLabelList[i].setIcon(currentEnemies.get(enemyKeyList[i]).getEnemyImageIcon());
                currentEnemies.get(enemyKeyList[i]).setLocation(new int[]{enemyLabelList[i].getX(), enemyLabelList[i].getY()});
                enemyHPLabelList[i].setIcon(currentEnemies.get(enemyKeyList[i]).getCurrentEnemyHPIcon());
                loserLabelList[i].setIcon(null);
                if (currentEnemies.get(enemyKeyList[i]).isDead()) {
                    loserLabelList[i].setIcon(Functions.readImage("loser"));
                }

            } catch (IndexOutOfBoundsException exception) {
                enemyLabelList[i].setIcon(null);
                enemyHPLabelList[i].setIcon(null);
                loserLabelList[i].setIcon(null);
            }
        }
    }

    private void setBossImageLabel() {
        if (Game.caterpillar.getCurrentLocation().isBossPresent()) {
            bossImageLabel.setIcon(Functions.readImage("bird"));
            for (JLabel hp : bossHPLabelList) {
                hp.setIcon(null);
            }
            for (int i = 0; i < 10 * Game.boss.getHealth() / Game.boss.getMaxHealth(); i++) {
                bossHPLabelList[i].setIcon(Functions.readImage("hpTile"));
            }
        } else {
            bossImageLabel.setIcon(null);
            for (JLabel hp : bossHPLabelList) {
                hp.setIcon(null);
            }
        }
    }

    private void setDiscriptionLabel() {

        TitledBorder description = new TitledBorder("Room Description");
        description.setTitleColor(Color.GREEN);
        descriptionLabel.setBorder(description);
        descriptionLabel.setPreferredSize(new Dimension(424, 200));

        String location = Game.caterpillar.getCurrentLocation().getName().toLowerCase();
        String desc = Game.caterpillar.getCurrentLocation().getDescription().toLowerCase();

        descriptionLabel.setText(

                "<html>\n" +
                        "<style>\n" +
                        "table {\n" +
                        "color:red;\n" +
                        "padding:15px;\n" +
                        "}\n" +
                        "</style>\n" +
                        "<table style=\"width:100%\">\n" +
                        "<tr>\n" +
                        "<td style=\"text-align: left; font-size:20px\">Location: " + location.toUpperCase() +
                        "</td>\n" +
                        "</tr>\n" +
                        "<tr>\n" +
                        "<td style=\"text-align: left; font-size:10px\">Desc: " + desc +
                        "</td>\n" +
                        "</tr>\n" +
                        "</table>\n" +
                        "\n" +
                        "</html>");

    }

    private void setMapPanel(JPanel locationPanel) {
        Location location = Game.caterpillar.getCurrentLocation();

        JPanel northRoom, southRoom, eastRoom, westRoom, currentRoom, emptyRoomNorthEast, emptyRoomNorthWest, emptyRoomSouthEast, emptyRoomSouthWest,
                emptyRoomNorth, emptyRoomSouth, emptyRoomWest, emptyRoomEast, emptyRoomCurrent;
        JPanel mapPanel = new JPanel();
        northRoom = new JPanel();
        southRoom = new JPanel();
        eastRoom = new JPanel();
        westRoom = new JPanel();
        currentRoom = new JPanel();
        emptyRoomNorthEast = new JPanel();
        emptyRoomNorthWest = new JPanel();
        emptyRoomSouthEast = new JPanel();
        emptyRoomSouthWest = new JPanel();


        TitledBorder map = new TitledBorder("Map");
        map.setTitleColor(Color.GREEN);
        mapLabel.setBorder(map);
        mapLabel.setPreferredSize(new Dimension(100, 15));
        locationPanel.add(mapLabel, BorderLayout.NORTH);

        mapPanel.setBackground(new Color(0, 0, 0));
        mapPanel.setLayout(new GridLayout(3, 3));
        mapPanel.setPreferredSize(new Dimension(100, 100));

        emptyRoomNorthEast.setBackground(new Color(0, 0, 0));
        emptyRoomNorthWest.setBackground(new Color(0, 0, 0));
        emptyRoomSouthEast.setBackground(new Color(0, 0, 0));
        emptyRoomSouthWest.setBackground(new Color(0, 0, 0));


        mapPanel.add(northEastLabel);
        mapPanel.add(northRoomLabel);
        mapPanel.add(emptyRoomNorthWest);
        mapPanel.add(westRoomLabel);
        mapPanel.add(currentRoomLabel);
        mapPanel.add(eastRoomLabel);
        mapPanel.add(emptyRoomSouthEast);
        mapPanel.add(southRoomLabel);
        mapPanel.add(emptyRoomSouthWest);
        locationPanel.add(mapPanel, BorderLayout.CENTER);
        setMapLabel();

    }

    private void setRoomPanel(JPanel panel) {
        JPanel roomPanel = new JPanel();
        enemyListLabel = new JLabel();
        itemListLabel = new JLabel();

        roomPanel.setBackground(new Color(0, 0, 0));
        roomPanel.setLayout(new BorderLayout());

        setItemListLabel();
        setEnemyListLabel();

        roomPanel.add(itemListLabel, BorderLayout.WEST);
        roomPanel.add(enemyListLabel, BorderLayout.EAST);

        panel.add(roomPanel, BorderLayout.SOUTH);

    }

    private void setMapLabel() {

        Location location = Game.caterpillar.getCurrentLocation();

        TitledBorder current = new TitledBorder("Current");
        current.setTitleColor(Color.GREEN);
        currentRoomLabel.setBorder(current);
        currentRoomLabel.setText(roomTextFormatter(location.getName()));


        if (location.getNorth().equals("DEAD_END")) {
            northRoomLabel.setBorder(null);
            northRoomLabel.setText("");
        } else {
            TitledBorder north = new TitledBorder("North");
            north.setTitleColor(Color.GREEN);
            northRoomLabel.setBorder(north);
            northRoomLabel.setText(roomTextFormatter(location.getNorth()));
        }


        if (location.getWest().equals("DEAD_END")) {
            westRoomLabel.setBorder(null);
            westRoomLabel.setText("");
        } else {
            TitledBorder west = new TitledBorder("West");
            west.setTitleColor(Color.GREEN);
            westRoomLabel.setBorder(west);
            westRoomLabel.setText(roomTextFormatter(location.getWest()));
        }

        if (location.getEast().equals("DEAD_END")) {
            eastRoomLabel.setBorder(null);
            eastRoomLabel.setText("");
        } else {
            TitledBorder east = new TitledBorder("East");
            east.setTitleColor(Color.GREEN);
            eastRoomLabel.setBorder(east);
            eastRoomLabel.setText(roomTextFormatter(location.getEast()));
        }

        if (location.getSouth().equals("DEAD_END")) {
            southRoomLabel.setBorder(null);
            southRoomLabel.setText("");
        } else {
            TitledBorder south = new TitledBorder("South");
            south.setTitleColor(Color.GREEN);
            southRoomLabel.setBorder(south);
            southRoomLabel.setText(roomTextFormatter(location.getSouth()));
        }
    }

    private String roomTextFormatter(String locationName) {
        String result = "<html>\n" +
                "<style>\n" +
                "table {\n" +
                "color:green;\n" +
                "font-size:10px;\n" +
                "padding:5px;\n" +
                "text-align: right;\n" +
                "}\n" +
                "</style>\n" +
                "<table style=\"width:100%\">\n" +
                "<tr>\n" +
                "<td>" + locationName +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "\n" +
                "</html>";
        return result;
    }

    private void setItemListLabel() {

        TitledBorder item = new TitledBorder("Item List");
        item.setTitleColor(Color.GREEN);
        itemListLabel.setPreferredSize(new Dimension(116, 200));
        itemListLabel.setBorder(item);

        String result = "<html>\n" +
                "<style>\n" +
                "table {\n" +
                "color:green;\n" +
                "font-size:10px;\n" +
                "padding: 15px;\n" +
                "text-align: right;\n" +
                "}\n" +
                "</style>\n" +
                "<table style=\"width:100%\"" +
                ">\n";


        for (Map.Entry<String, Item> entry : Game.caterpillar.getCurrentLocation().getItems().entrySet()) {
            result += "<tr>\n" + "<td style=\"text-align: right; padding-right: 0px;\">" + entry.getKey().toUpperCase() + "<br> * " + entry.getValue().getQty() + "</td>\n" + " </tr>\n";
        }

        result +=
                "</table>\n" +
                        "\n" +
                        "</html>";

        itemListLabel.setText(result);

    }

    private void setEnemyListLabel() {

        TitledBorder enemy = new TitledBorder("Enemy List");
        enemy.setTitleColor(Color.GREEN);
        enemyListLabel.setPreferredSize(new Dimension(116, 200));
        enemyListLabel.setBorder(enemy);

        String result = "<html>\n" +
                "<style>\n" +
                "table {\n" +
                "color:green;\n" +
                "font-size:10px;\n" +
                "padding:20px;\n" +
                "}\n" +
                "</style>\n" +
                "<table style=\"width:100%\">\n";

        for (Map.Entry<String, Enemy> entry : Game.caterpillar.getCurrentLocation().getEnemies().entrySet()) {
            if (!entry.getValue().isDead()) {
                result += "<tr>\n" + "<td{\"text-align: right;\\n\"}>" + " " + entry.getKey().toUpperCase() + "<br>hp: " + entry.getValue().getHealth() + "</td>\n" + "</tr>\n";
            }
        }
        result += "</table>\n" +
                "\n" +
                "</html>";
        enemyListLabel.setText(result);
    }

    public void updateLabels() {

        setUpLastMoveLabel();
        setCaterpillarStatLabel();
        setEnemyStatLabel();
        setDiscriptionLabel();
        setMapLabel();
        setEnemyListLabel();
        setItemListLabel();
        setImageLabels();
        Game.caterpillar.setLastAction("-----------------------");
        Game.window.repaint();
    }

    private void setSoundPanel() {
        mu = new Music();
        setUpSoundButton();
        SliderSetup();
        soundPanel = new JPanel(new BorderLayout());
        soundPanel.add(slider, BorderLayout.SOUTH);
        soundPanel.add(soundButton, BorderLayout.NORTH);
    }

    private void SliderSetup() {
        // Set the panel to add buttons
//        panel1 = new JPanel();
//        panel1.setBackground(new Color(0,0,0));
//        TitledBorder SliderBoarder = new TitledBorder("Volume Control");
//        SliderBoarder.setTitleColor(Color.GREEN);
//        panel1.setBorder(SliderBoarder);
//        panel1.setPreferredSize(new Dimension(424, 70));


//        Game.window.add(panel1, BorderLayout.BEFORE_FIRST_LINE);


        // Add status label to show the status of the slider
//        JLabel status = new JLabel("Slide the Slider to Increase/Decrease Volume", JLabel.HORIZONTAL);
//        status.setVisible(true);
//        status.setForeground(Color.GREEN);
        // Set the slider
        slider = new JSlider(JSlider.HORIZONTAL, -66, 6, 6);
        slider.setMinorTickSpacing(6);
        slider.setPaintTicks(true);
//        slider.setBackground(new Color(0,0,0));
        slider.setForeground(Color.GREEN);

        // Set the labels to be painted on the slider
        slider.setPaintLabels(true);


        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int volume = slider.getValue();
                //  status.setText(""+volume);
                FloatControl newVolume = (FloatControl) mu.clip.getControl(FloatControl.Type.MASTER_GAIN);
                newVolume.setValue(-10.0f);
                newVolume.setValue((float) volume);
            }
        });
    }

//    private JPanel panel1;
//    private JLabel status;
    // Add positions label in the slider
//        Hashtable<Integer, JLabel> position = new Hashtable<Integer, JLabel>();
//        //   Hashtable position = new Hashtable();
////        position.put(-66, new JLabel("<html><font color='red'>Min</font></html>"));
////     //   position.put(-40, new JLabel("<html><font color='red'>25</font></html>"));
////   //    position.put(-20, new JLabel("<html><font color='red'>50</font></html>"));
////    //   position.put(0, new JLabel("<html><font color='red'>75</font></html>"));
////        position.put(6, new JLabel("<html><font color='red'>Max</font></html>"));
//        slider.setLabelTable(position);


    // Add change listener to the slider

    //     volumecontrol(val);
    // Add the slider to the panel
    private void setUpSoundButton() {

        ButtonHandler bHandler = new ButtonHandler();
        soundButton = new JButton("Hot Tunes!");
        soundButton.setPreferredSize(new Dimension(200, 20));
        soundButton.setFocusPainted(false);
        soundButton.addActionListener(bHandler);
        soundButton.setActionCommand("buttonClick");
        rickRoll = "/audio/never.wav";
        musicOnOff = "off";
    }

    private class Music {
        Clip clip;

        public void setFile(String soundFileName) {
            try {
                // File file = new File(soundFileName);
                AudioInputStream sound = AudioSystem.getAudioInputStream(GamePanel.class.getResource(soundFileName));
                clip = AudioSystem.getClip();
                clip.open(sound);
            } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
                e.printStackTrace();
            }
        }

        public void play() {
            clip.setFramePosition(0);
            clip.start();
        }

        public void loop() {
            clip.loop(clip.LOOP_CONTINUOUSLY);
        }

        public void stop() {
            clip.stop();
            clip.close();
        }
    }

    private class ButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String clickedButton = event.getActionCommand();

            switch (clickedButton) {
                case "buttonClick":
                    if (musicOnOff.equals("off")) {
                        mu.setFile(rickRoll);
                        mu.play();
                        mu.loop();
                        musicOnOff = "on";
                        soundButton.setText("Hot Tunes Playing!!!");
                    } else if (musicOnOff.equals("on")) {
                        mu.stop();
                        musicOnOff = "off";
                        soundButton.setText("No More Hot Tunes");
                    }
                    break;
            }
        }
    }
}