package com.game.view;

import com.game.controller.Game;
import com.game.model.materials.Enemy;
import com.game.model.materials.Item;
import com.game.model.materials.Location;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ViewWindow {


    private JFrame window;
    private JLabel lastMoveLabel;
    private JLabel caterpillarStatLabel;
    private JLabel enemyStatLabel;
    private JLabel descriptionLabel;
    private JLabel enemyListLabel;
    private JLabel itemListLabel;

    private JTextField inputField;
    private JPanel inputPanel;
    private JPanel statPanel;
    private JPanel locationPanel;
    private KeyListener listener;
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
    private JPanel animationPanel;
    private JLabel caterpillarImageLabel;
    private JLabel backgroundLabel;
    private JLabel item1Label;
    private JLabel item2Label;
    private JLabel item3Label;
    private JLabel enemy1Label;
    private JLabel enemy2Label;
    private JLabel enemy3Label;
    private ArrayList<JLabel> itemLabelList;
    private ArrayList<JLabel> enemyLabelList;


    public ViewWindow() {
        this.window = new JFrame("A Grub's Life.");
        this.window.setLayout(new BorderLayout());
        this.window.setPreferredSize(new Dimension(1024, 768));
        this.window.setVisible(true);
        this.window.setResizable(true);
//        this.window.setLocationRelativeTo(null);
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.window.pack();
        setUpInputPanel();
        setUpDescriptionPanel();


    }

    private void setUpInputPanel() {
        JPanel inputPanel = new JPanel();
        this.lastMoveLabel = new JLabel();
        this.inputField = new JTextField(50);
        Color background = new Color(10, 80, 20, 158);
        inputPanel.setLayout(new BorderLayout());
        inputPanel.setBorder(BorderFactory.createLineBorder(new Color(110, 16, 5)));
        inputPanel.setBackground(background);
        inputPanel.setPreferredSize(new Dimension(1000, 200));

        setUpInputField(inputPanel);
        setUpLastMoveLabel();
        inputPanel.add(inputField, BorderLayout.NORTH);
        inputPanel.add(lastMoveLabel, BorderLayout.CENTER);
        this.window.add(inputPanel, BorderLayout.SOUTH);


    }

    private void setUpInputField(JPanel inputPanel) {

        inputField.setBorder(BorderFactory.createTitledBorder("Enter your command as a [VERB/NOUN]: \n " +
                ""));
        inputField.setBackground(new Color(217, 224, 214));
        inputField.addActionListener(e -> {

            String input = inputField.getText();
            Game.getProcessor().processCommand(input);
            inputField.setText("");
            updateCaterpillarStatus();

        });
    }

    private void setUpLastMoveLabel() {

        String lastAction = Game.caterpillar.getLastAction();
        System.out.println(lastAction);
        lastMoveLabel.setBorder(BorderFactory.createTitledBorder("Your Last Move"));
        if (lastAction.length() > 0) {
            lastMoveLabel.setText("<html> " +
                    "<h1>" + lastAction + "</h1>" +
                    "</html>");

        } else {

            lastMoveLabel.setText("<html><body>" +
                    "                                  " +
                    "<body></html>");

        }

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

        this.window.add(statPanel, BorderLayout.EAST);


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

//        locationPanel.add(roomLabel);
        this.window.add(locationPanel, BorderLayout.WEST);


    }

    private void setUpDescriptionPanel() {
        JPanel descriptionPanel = new JPanel(null);
        item1Label = new JLabel();
        item2Label = new JLabel();
        item3Label = new JLabel();
        enemy1Label = new JLabel();
        enemy2Label = new JLabel();
        enemy3Label = new JLabel();


        itemLabelList = new ArrayList<JLabel>(Arrays.asList(item1Label, item2Label, item3Label));
        enemyLabelList = new ArrayList<JLabel>(Arrays.asList(enemy1Label, enemy2Label, enemy3Label));

        animationPanel = new JPanel(null);
        descriptionLabel = new JLabel();
        backgroundLabel = new JLabel();
        caterpillarImageLabel = new JLabel();



        descriptionPanel.setBackground(new Color(0, 0, 0));
        animationPanel.setBackground(new Color(0, 0, 0));

        descriptionPanel.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255)));
        animationPanel.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255)));


        descriptionPanel.add(descriptionLabel);
        descriptionPanel.add(animationPanel);

        descriptionLabel.setBounds(0, 0, 550, 195);
        animationPanel.setBounds(0, 195, 550, 335);
        backgroundLabel.setBounds(5, 5, 540, 325);
        caterpillarImageLabel.setBounds(220, 200, 100, 100);



        item1Label.setBounds(100,230, 80, 80);
        item2Label.setBounds(20,230, 80, 80);
        item3Label.setBounds(10,350, 80, 80);
        enemy1Label.setBounds(100,80, 80, 80);
        enemy2Label.setBounds(340,80, 80, 80);
        enemy3Label.setBounds(220,50, 80, 80);

        animationPanel.add(item1Label);
        animationPanel.add(item2Label);
        animationPanel.add(item3Label);
        animationPanel.add(enemy1Label);
        animationPanel.add(enemy2Label);
        animationPanel.add(enemy3Label);

        animationPanel.add(caterpillarImageLabel);
        animationPanel.add(backgroundLabel);


        this.window.add(descriptionPanel, BorderLayout.CENTER);

        setDiscriptionLabel();
        setImageLabels();

//        setInstructionLabel();

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
                "</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td style=\"text-align: left;\">Level: </td><td>" + Game.caterpillar.getLevel() +
                "</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td style=\"text-align: left;\">Experience: </td><td>" + Game.caterpillar.getExperience() +
                "/" + Game.caterpillar.getMaxExperience() +
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


        if (Game.caterpillar.getEngagedEnemy() != null) {
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
                            Game.caterpillar.getEngagedEnemy().getName() +
                            "</td>\n" +
                            "</tr>\n" +
                            "<tr>\n" +
                            "<td style=\"text-align: left;\">Strength: </td><td>" +
                            Game.caterpillar.getEngagedEnemy().getStrength() +
                            "</td>\n" +
                            "</tr>\n" +
                            "<tr>\n" +
                            "<td style=\"text-align: left;\">Health: </td><td>" +
                            Game.caterpillar.getEngagedEnemy().getHealth() +
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
        item1Label.setIcon(null);
        item2Label.setIcon(null);
        item3Label.setIcon(null);
        enemy1Label.setIcon(null);
        enemy2Label.setIcon(null);
        enemy3Label.setIcon(null);

        backgroundLabel.setIcon(Game.caterpillar.getCurrentLocation().getBackgroundImageIcon());
        caterpillarImageLabel.setIcon(Game.caterpillar.getCaterpillarImageIcon());
        HashMap<String, Item> currentItems = new HashMap<>();
        HashMap<String, Enemy> currentEnemies = new HashMap<>();
        int itemCounter = 0;
        int enemyCounter = 0;
        currentItems = Game.caterpillar.getCurrentLocation().getItems();
        currentEnemies = Game.caterpillar.getCurrentLocation().getEnemies();

        for (Map.Entry<String, Item> entry : currentItems.entrySet()) {
            itemLabelList.get(itemCounter).setIcon(entry.getValue().getItemImageIcon());
            itemCounter++;
        }
        for (Map.Entry<String, Enemy> entry : currentEnemies.entrySet()){
            enemyLabelList.get(enemyCounter).setIcon(entry.getValue().getEnemyImageIcon());
            enemyCounter++;
        }
    }

    private void setInstructionLabel() {
        descriptionLabel.setForeground(Color.red);
        try {
            //open the file
            FileInputStream inMessage = new FileInputStream("src/main/resources/GameInstructions.txt");
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(inMessage);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                // Print the content on the console
                // System.out.println (strLine);
                //br.append(strLine+"/n");
                //       descriptionLabel.setText(strLine+"/n");
                // descriptionLabel.setText( descriptionLabel.getText()+strLine+"/n");

                descriptionLabel.setText(descriptionLabel.getText() + "<html> <br/> <html/>" + strLine);


            }
            //Close the input stream
            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
            System.out.println(entry.getKey());
            result += "<tr>\n" + "<td{\"text-align: right;\\n\"}>" + " " + entry.getKey().toUpperCase() + "<br>hp: " + entry.getValue().getHealth() + "</td>\n" + "</tr>\n";
        }
        result += "</table>\n" +
                "\n" +
                "</html>";
        enemyListLabel.setText(result);
    }

    public void initSidePanel() {
        setUpLocationPanel();
        setUpStatPanel();
    }

    public void updateCaterpillarStatus() {
        setUpLastMoveLabel();
        setCaterpillarStatLabel();
        setEnemyStatLabel();
        setDiscriptionLabel();
        setMapLabel();
        setEnemyListLabel();
        setItemListLabel();
        setImageLabels();

        Game.caterpillar.checkDeathAndImage();
        this.window.repaint();
    }

    public JPanel getLocationPanel() {
        return locationPanel;
    }
}
