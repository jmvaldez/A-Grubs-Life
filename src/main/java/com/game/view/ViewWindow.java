
package com.game.view;

import com.game.controller.Game;
import com.game.model.materials.Enemy;
import com.game.model.materials.Item;
import com.game.model.materials.Location;



import javax.print.attribute.standard.Media;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.*;
import javax.swing.event.*;
import javax.sound.sampled.Mixer.Info;

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
    private JLabel item1QtyLabel;
    private JLabel item2QtyLabel;
    private JLabel enemy1HPLabel;
    private JLabel enemy2HPLabel;
    private JLabel enemy3HPLabel;
    private ArrayList<JLabel> itemLabelList;
    private ArrayList<JLabel> enemyLabelList;
    private ArrayList<JLabel> itemQtyLabelList;
    private ArrayList<JLabel> enemyHPLabelList;
    private JButton soundButton;
    private String rickRoll;
    private String musicOnOff;
    private ButtonHandler bHandler;
    private Music mu;
    //////
    private JSlider slider;
    private JPanel panel1;
    private JLabel status;
    private float val;
    private ChangeListener ChangeListener;



    public ViewWindow() {

        this.window = new JFrame("A Grub's Life.");

        this.window.setLayout(new BorderLayout());
        this.window.setPreferredSize(new Dimension(1024, 768));
        this.window.setVisible(true);
        this.window.setResizable(true);
//        this.window.setLocationRelativeTo(null);
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.window.pack();
        bHandler = new ButtonHandler();
        mu = new Music();
        /////


        ////
        setUpSoundButton();
        setUpInputPanel();
        setUpDescriptionPanel();
        SliderSetup();


    }

    private void setUpSoundButton() {

        soundButton = new JButton("Hot Tunes!");
        soundButton.setPreferredSize(new Dimension(200, 20));
        soundButton.setFocusPainted(false);
        soundButton.addActionListener(bHandler);
        soundButton.setActionCommand("buttonClick");
        rickRoll = "/audio/never.wav";
        musicOnOff = "off";
    }

    /////////////////////////////////////////////////

    private void SliderSetup(){
        // Set the panel to add buttons
        JPanel panel1 = new JPanel();
        panel1.setBackground(new Color(0,0,0));
        TitledBorder SliderBoarder = new TitledBorder("Volume Control");
        SliderBoarder.setTitleColor(Color.GREEN);
        panel1.setBorder(SliderBoarder);
        panel1.setPreferredSize(new Dimension(424, 70));

      this.window.add(panel1, BorderLayout.BEFORE_FIRST_LINE);


        // Add status label to show the status of the slider
        JLabel status = new JLabel("Slide the Slider to Increase/Decrease Volume", JLabel.HORIZONTAL);
        status.setVisible(true);
        status.setForeground(Color.GREEN);
        // Set the slider
        JSlider slider = new JSlider(JSlider.HORIZONTAL,0,100,50);
        slider.setMinorTickSpacing(10);
        slider.setPaintTicks(true);
        slider.setBackground(new Color(0,0,0));
        slider.setForeground(Color.GREEN);

        // Set the labels to be painted on the slider
        slider.setPaintLabels(true);


        // Add positions label in the slider
       Hashtable<Integer, JLabel> position = new Hashtable<Integer, JLabel>();
     //   Hashtable position = new Hashtable();
        position.put(0, new JLabel("<html><font color='red'>Min</font></html>"));
       position.put(25, new JLabel("<html><font color='red'>25</font></html>"));
        position.put(50, new JLabel("<html><font color='red'>50</font></html>"));
      position.put(75, new JLabel("<html><font color='red'>75</font></html>"));
       position.put(100, new JLabel("<html><font color='red'>Max</font></html>"));
       slider.setLabelTable(position);




        // Add change listener to the slider
        slider.addChangeListener(new ChangeListener() {
          public void stateChanged(ChangeEvent e) {
             status.setText("Volume is: " + ((JSlider)e.getSource()).getValue());
               JSlider source = (JSlider)e.getSource();


               val = source.getValue();

           //     volumecontrol(val);




       }
       });

        // Add the slider to the panel
        panel1.add(slider);
        panel1.setVisible(true);
        panel1.add(status);

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
        lastMoveLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        lastMoveLabel.setPreferredSize(new Dimension(1024,135));


        setUpInputField(inputPanel);
        setUpLastMoveLabel();
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(lastMoveLabel, BorderLayout.NORTH);
        inputPanel.add(soundButton, BorderLayout.SOUTH);
        this.window.add(inputPanel, BorderLayout.SOUTH);


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
        if (lastAction.length() > 0) {
            lastMoveLabel.setText("<html> " +
                    "<h3>" + lastAction + "</h3>" +
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
        item1QtyLabel = new JLabel();
        item2QtyLabel = new JLabel();
        item3Label = new JLabel();
        enemy1Label = new JLabel();
        enemy2Label = new JLabel();
        enemy3Label = new JLabel();
        enemy1HPLabel = new JLabel();
        enemy2HPLabel = new JLabel();
        enemy3HPLabel = new JLabel();


        itemLabelList = new ArrayList<JLabel>(Arrays.asList(item1Label, item2Label, item3Label));
        enemyLabelList = new ArrayList<JLabel>(Arrays.asList(enemy1Label, enemy2Label, enemy3Label));
        itemQtyLabelList = new ArrayList<JLabel>(Arrays.asList(item1QtyLabel, item2QtyLabel));
        enemyHPLabelList =  new ArrayList<JLabel>(Arrays.asList(enemy1HPLabel, enemy2HPLabel, enemy3HPLabel));

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
        caterpillarImageLabel.setBounds(210, 190, 100, 100);


        item1Label.setBounds(100, 230, 80, 80);
        item2Label.setBounds(20, 230, 80, 80);
        item1QtyLabel.setBounds(160, 300, 30, 30);
        item2QtyLabel.setBounds(80, 300, 30, 30);
        item3Label.setBounds(10, 350, 80, 80);
        enemy1Label.setBounds(100, 80, 80, 80);
        enemy2Label.setBounds(340, 80, 80, 80);
        enemy3Label.setBounds(220, 50, 80, 80);
        enemy1HPLabel.setBounds(100, 70, 80, 10);
        enemy2HPLabel.setBounds(340, 70, 80, 10);
        enemy3HPLabel.setBounds(220, 40, 80, 10);


        item1QtyLabel.setForeground(Color.RED);
        item2QtyLabel.setForeground(Color.RED);
        item1QtyLabel.setFont(new Font("Serif", Font.BOLD, 15));
        item2QtyLabel.setFont(new Font("Serif", Font.BOLD, 15));

        animationPanel.add(item1QtyLabel);
        animationPanel.add(item2QtyLabel);
        animationPanel.add(enemy1HPLabel);
        animationPanel.add(enemy2HPLabel);
        animationPanel.add(enemy3HPLabel);
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
        item1Label.setIcon(null);
        item2Label.setIcon(null);
        item3Label.setIcon(null);
        enemy1Label.setIcon(null);
        enemy2Label.setIcon(null);
        enemy3Label.setIcon(null);
        item1QtyLabel.setText(null);
        item2QtyLabel.setText(null);
        enemy1HPLabel.setIcon(null);
        enemy2HPLabel.setIcon(null);
        enemy3HPLabel.setIcon(null);

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
            itemQtyLabelList.get(itemCounter).setText("x " + String.valueOf(entry.getValue().getQty()));
            itemCounter++;
        }
        for (Map.Entry<String, Enemy> entry : currentEnemies.entrySet()) {
            enemyLabelList.get(enemyCounter).setIcon(entry.getValue().getEnemyImageIcon());
            enemyHPLabelList.get(enemyCounter).setIcon(entry.getValue().getCurrentEnemyHPIcon());
            enemyCounter++;
        }
    }

    private void setInstructionLabel() {
        descriptionLabel.setForeground(Color.red);
        try {
            //open the file
            FileInputStream inMessage = new FileInputStream("/json/GameInstructions.txt");
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

    public void updateLabels() {
        setUpLastMoveLabel();
        setCaterpillarStatLabel();
        setEnemyStatLabel();
        setDiscriptionLabel();
        setMapLabel();
        setEnemyListLabel();
        setItemListLabel();
        setImageLabels();
        this.window.repaint();
        Game.caterpillar.engagedEnemy = null;
    }

    public JPanel getLocationPanel() {
        return locationPanel;
    }

    public class Music {
        Clip clip;

        public void setFile(String soundFileName) {

            try {
                // File file = new File(soundFileName);
                AudioInputStream sound = AudioSystem.getAudioInputStream(ViewWindow.class.getResource(soundFileName));
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

    public class ButtonHandler implements ActionListener {
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
                        soundButton.addChangeListener(ChangeListener);
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