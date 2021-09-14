package com.game.view;

import com.game.controller.Game;
import com.game.model.materials.Location;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyListener;
import java.io.*;
import java.nio.CharBuffer;

public class ViewWindow {


    private JFrame window;
    private JLabel lastMoveLabel;
    private JLabel caterpillarStatLabel;
    private JLabel enemyStatLabel;
    private JLabel descriptionLabel;
    private JLabel roomLabel;

    private JTextField inputField;
    private JPanel inputPanel;
    private JPanel statPanel;
    private JPanel descriptionPanel;
    private JPanel locationPanel;
    private KeyListener listener;
    private JLabel mapLabel;
    private JLabel northRoomLabel;
    private JLabel southRoomLabel;
    private JLabel eastRoomLabel;
    private JLabel westRoomLabel;
    private JLabel currentRoomLabel;
    private JLabel emptyLabel;


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
        setUpStatPanel();
        setUpDescriptionPanel();
        setUpLocationPanel();


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
//        statPanel.setLayout(new GridLayout(0, 1));
        statPanel.setBackground(new Color(0, 0, 0));
        statPanel.setPreferredSize(new Dimension(300,800));
        setCaterpillarStatLabel();
        setEnemyStatLabel();
        statPanel.add(caterpillarStatLabel,BorderLayout.NORTH);
        statPanel.add(enemyStatLabel, BorderLayout.SOUTH);

        this.window.add(statPanel, BorderLayout.EAST);


    }

    private void setCaterpillarStatLabel() {

        caterpillarStatLabel.setText("<html>\n" +
                "<style>\n" +
                "table {\n" +
                "color:green;\n" +
                "font-size:20px;\n" +
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
        caterpillarStatLabel.setPreferredSize(new Dimension(300, 300));

    }

    private void setEnemyStatLabel() {


        if (Game.caterpillar.getCurrentLocation().getEnemy() != null) {
            enemyStatLabel.setText(
                    "<html>\n" +
                            "<style>\n" +
                            "table {\n" +
                            "color:green;\n" +
                            "font-size:20px;\n" +
                            "padding:15px;\n" +
                            "}\n" +
                            "</style>\n" +
                            "<table style=\"width:5%\">\n" +
                            "<tr>\n" +
                            "<td style=\"text-align: left;\">Strength: </td><td>" +
                            Game.caterpillar.getCurrentLocation().getEnemy().getStrength() +
                            "</td>\n" +
                            "</tr>\n" +
                            "<tr>\n" +
                            "<td style=\"text-align: left;\">Health: </td><td>" +
                            Game.caterpillar.getCurrentLocation().getEnemy().getHealth() +
                            "</td>\n" +
                            "</tr>\n" +
                            "</table>\n" +
                            "\n" +
                            "</html>");

        } else {
            enemyStatLabel.setText("");
        }

        enemyStatLabel.setBorder(BorderFactory.createTitledBorder(Game.caterpillar.getCurrentLocation().getEnemy().getName()));
        TitledBorder eb = new TitledBorder("NO Enemy");
        eb.setTitle(Game.caterpillar.getCurrentLocation().getEnemy().getName() + " Stats");
        eb.setTitleColor(Color.GREEN);
        enemyStatLabel.setBorder(eb);
        enemyStatLabel.setPreferredSize(new Dimension(300, 220));

    }

    private void setUpDescriptionPanel() {
        JPanel descriptionPanel = new JPanel();
        this.descriptionLabel = new JLabel();
        descriptionPanel.setBackground(new Color(0, 0, 0));
        descriptionPanel.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255)));
        setDiscriptionLabel();
        descriptionPanel.add(descriptionLabel, BorderLayout.CENTER);
        this.window.add(descriptionPanel, BorderLayout.CENTER);


    }

    private void setDiscriptionLabel() {
        descriptionLabel.setForeground (Color.red);
        try {
            //open the file
            FileInputStream inMessage = new FileInputStream("src/main/resources/GameInstructions.txt");
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(inMessage);
           BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null)   {
                // Print the content on the console
               // System.out.println (strLine);
               //br.append(strLine+"/n");
         //       descriptionLabel.setText(strLine+"/n");
               // descriptionLabel.setText( descriptionLabel.getText()+strLine+"/n");

                descriptionLabel.setText(descriptionLabel.getText()+ "<html> <br/> <html/>" + strLine);
            }
            //Close the input stream
            br.close();

       } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }





/*
        try {
            //open the file
            FileInputStream inMessage = new FileInputStream("File/GameInstructions.txt");
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(inMessage);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null)   {
                // Print the content on the console
                //System.out.println (strLine);
                descriptionLabel.setText(strLine);
            }
            //Close the input stream
            in.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

*/

/*

        String str = null;
        try {
            File file = new File("File/GameInstructions.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((str = br.readLine()) != null) {
             //   System.out.println (str);


            }
        }catch(IOException e) {
            e.printStackTrace();
        }

        descriptionLabel.setText(str);
*/

/*
        String location = Game.caterpillar.getCurrentLocation().getName().toLowerCase();
        String desc = Game.caterpillar.getCurrentLocation().getDescription().toLowerCase();

       descriptionLabel.setLocation(100,100);
        descriptionLabel.setText("<html> " +
                "<style>" +
                "p {padding-bottom: 280px }" +
                "</style>" +
                "<a href=\"https://en.wikipedia.org/wiki/Caterpillar\">Caterpillar Wiki</a>" +
                "<h1> " + location + "</h1> <br>" +
                "<p> " + desc + "</p><br><br><br><br>" +
                "  </html>\n");
*/
    }


    private void setUpLocationPanel() {
        JPanel locationPanel = new JPanel();

        locationPanel.setLayout(new BorderLayout());
        locationPanel.setBackground(new Color(0, 0, 0));
        locationPanel.setPreferredSize(new Dimension(300,800));


        setMapPanel(locationPanel);
        setRoomPanel(locationPanel);

//        locationPanel.add(roomLabel);
        this.window.add(locationPanel, BorderLayout.WEST);




    }

    private void setMapPanel(JPanel locationPanel) {
        JPanel mapPanel, northRoom, southRoom, eastRoom, westRoom, currentRoom, emptyRoomNorthEast, emptyRoomNorthWest, emptyRoomSouthEast, emptyRoomSouthWest;
        mapPanel = new JPanel();
        northRoom = new JPanel();
        southRoom = new JPanel();
        eastRoom = new JPanel();
        westRoom = new JPanel();
        currentRoom = new JPanel();
        emptyRoomNorthEast = new JPanel();
        emptyRoomNorthWest = new JPanel();
        emptyRoomSouthEast = new JPanel();
        emptyRoomSouthWest = new JPanel();

        northRoomLabel = new JLabel();
        southRoomLabel = new JLabel();
        eastRoomLabel = new JLabel();
        westRoomLabel = new JLabel();
        currentRoomLabel = new JLabel();
        emptyLabel = new JLabel();
        mapLabel = new JLabel();




        TitledBorder map = new TitledBorder("Map");
        map.setTitleColor(Color.GREEN);
        mapLabel.setBorder(map);
        mapLabel.setPreferredSize(new Dimension(100, 15));
        locationPanel.add(mapLabel,BorderLayout.NORTH);
//
//        TitledBorder map = new TitledBorder("Map");
//        map.setTitleColor(Color.GREEN);
//        mapLabel.setBorder(map);

        mapPanel.setBackground(new Color(0, 0, 0));
        mapPanel.setLayout(new GridLayout(3, 3));

        emptyRoomNorthEast.setBackground(new Color(0, 0, 0));
        emptyRoomNorthWest.setBackground(new Color(0, 0, 0));
        emptyRoomSouthEast.setBackground(new Color(0, 0, 0));
        emptyRoomSouthWest.setBackground(new Color(0, 0, 0));

        mapPanel.add(emptyRoomNorthEast);


        TitledBorder north = new TitledBorder("North");
        north.setTitleColor(Color.GREEN);
        northRoomLabel.setBorder(north);
        mapPanel.add(northRoomLabel);

        mapPanel.add(emptyRoomNorthWest);

        TitledBorder west = new TitledBorder("West");
        west.setTitleColor(Color.GREEN);
        westRoomLabel.setBorder(west);
        mapPanel.add(westRoomLabel);

        TitledBorder current = new TitledBorder("Current");
        current.setTitleColor(Color.GREEN);
        currentRoomLabel.setBorder(current);
        mapPanel.add(currentRoomLabel);


        TitledBorder east = new TitledBorder("East");
        east.setTitleColor(Color.GREEN);
        eastRoomLabel.setBorder(east);
        mapPanel.add(eastRoomLabel);

        mapPanel.add(emptyRoomSouthEast);

        TitledBorder south = new TitledBorder("South");
        south.setTitleColor(Color.GREEN);
        southRoomLabel.setBorder(south);
        mapPanel.add(southRoomLabel);

        mapPanel.add(emptyRoomSouthWest);


        locationPanel.add(mapPanel, BorderLayout.CENTER);
        setMapLabel();

    }

    private void setRoomPanel(JPanel panel) {
        JPanel roomPanel = new JPanel();
        roomLabel = new JLabel();
//
//        TitledBorder map = new TitledBorder("Map");
//        map.setTitleColor(Color.GREEN);
//        mapLabel.setBorder(map);

        roomPanel.setBackground(new Color(0, 0, 0));


        TitledBorder room = new TitledBorder("Room");
        room.setTitleColor(Color.GREEN);
        roomLabel.setBorder(room);
        roomLabel.setPreferredSize(new Dimension(100, 200));

        panel.add(roomLabel, BorderLayout.SOUTH);

    }

    private void setMapLabel() {

        Location location = Game.caterpillar.getCurrentLocation();

        currentRoomLabel.setText("<html>\n" +
                "<style>\n" +
                "table {\n" +
                "color:green;\n" +
                "font-size:10px;\n" +
                "padding:10px;\n" +
                "}\n" +
                "</style>\n" +
                "<table style=\"width:5%\">\n" +
                "<tr>\n" +
                "<td>" + location.getName() +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "\n" +
                "</html>");

        northRoomLabel.setText("<html>\n" +
                "<style>\n" +
                "table {\n" +
                "color:green;\n" +
                "font-size:10px;\n" +
                "padding:10px;\n" +
                "}\n" +
                "</style>\n" +
                "<table style=\"width:5%\">\n" +
                "<tr>\n" +
                "<td>" + location.getNorth() +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "\n" +
                "</html>");

        southRoomLabel.setText("<html>\n" +
                "<style>\n" +
                "table {\n" +
                "color:green;\n" +
                "font-size:10px;\n" +
                "padding:10px;\n" +
                "}\n" +
                "</style>\n" +
                "<table style=\"width:5%\">\n" +
                "<tr>\n" +
                "<td>" + location.getSouth() +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "\n" +
                "</html>");
        eastRoomLabel.setText("<html>\n" +
                "<style>\n" +
                "table {\n" +
                "color:green;\n" +
                "font-size:10px;\n" +
                "padding:10px;\n" +
                "}\n" +
                "</style>\n" +
                "<table style=\"width:5%\">\n" +
                "<tr>\n" +
                "<td>" + location.getEast() +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "\n" +
                "</html>");
        westRoomLabel.setText("<html>\n" +
                "<style>\n" +
                "table {\n" +
                "color:green;\n" +
                "font-size:10px;\n" +
                "padding:10px;\n" +
                "}\n" +
                "</style>\n" +
                "<table style=\"width:5%\">\n" +
                "<tr>\n" +
                "<td>" + location.getWest() +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "\n" +
                "</html>");
    }


    private void setRoomLabel() {

        TitledBorder room = new TitledBorder("Room");
        room.setTitleColor(Color.GREEN);
        roomLabel.setBorder(room);


    }

    public void updateCaterpillarStatus() {
        setUpLastMoveLabel();
        setCaterpillarStatLabel();
        setEnemyStatLabel();
        setDiscriptionLabel();
        setRoomLabel();
        setMapLabel();
        Game.caterpillar.checkDeath();
        this.window.repaint();
    }

}
