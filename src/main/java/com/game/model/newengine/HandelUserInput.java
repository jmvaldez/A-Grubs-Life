package main.java.com.game.model.newengine;

import com.game.controller.Game;

public class HandelUserInput {

    public HandelUserInput(String input) {
        while (true) {
            try {
                String[] actionInput = input.toLowerCase().split(" ");

                processInput(actionInput);
                break;
            } catch (Exception e) {
                Game.caterpillar.setLastAction("invalid retype");
            }
        }
    }

    public void processInput(String[] input) {


    }

}
