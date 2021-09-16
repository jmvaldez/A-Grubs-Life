package com.game.model.engine;

import com.game.controller.Game;
import com.game.exception.DeadPlayerInputException;
import com.game.exception.InputLengthException;
import com.game.view.GameAudio;

import java.util.ArrayList;

public class LogicEngine {
    private CommandProcessor commandProcessor;
    private TextParser textParser;
    private ArrayList<String> parsedInput;

    public LogicEngine() {
        this.textParser = new TextParser();
        this.commandProcessor = new CommandProcessor();
    }

    public void processUserInput(String userInput) {//
        try {
            if (Game.caterpillar.isDead()) {
                parsedInput = textParser.parserDeadInput(userInput);
            } else {
                parsedInput = textParser.parserLiveInput(userInput);
            }
            commandProcessor.executeCommand(parsedInput);
        } catch (InputLengthException e) {
            System.out.println("Exception: [LogicEngine/processCommand/textParser/parseInput], Message: " + e.getMessage());
            GameAudio.PlayICANTAudio();
        } catch (DeadPlayerInputException e){
            System.out.println("Exception: [LogicEngine/processCommand/textParser/parseInput], Message: " + e.getMessage());
            GameAudio.PlayICANTAudio();
        }

        catch (Exception e) {
            System.out.println("Exception: [LogicEngine/processCommand], Message: " + e.getMessage());
            GameAudio.PlayICANTAudio();

        }
    }

}
