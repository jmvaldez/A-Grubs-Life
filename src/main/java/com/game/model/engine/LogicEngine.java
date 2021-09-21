package com.game.model.engine;

import com.game.controller.Game;
import com.game.exception.DeadPlayerInputException;
import com.game.exception.LivePlayerInputException;
import com.game.util.GameAudio;

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
            Functions.lotteryBossPresent();


        } catch (LivePlayerInputException | DeadPlayerInputException e) {
            GameAudio.PlayICANTAudio();
            System.out.println(
                    "Exception: [LogicEngine/processCommand/textParser/parseInput], Message: " + e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            GameAudio.PlayICANTAudio();
            System.out.println(
                    "Exception: [LogicEngine/processCommand /textParser/parseInput], Message: " + e.getMessage());
        } catch (Exception e) {
            GameAudio.PlayICANTAudio();
            System.out.println(
                    "Exception: UNKNOWN from LogicEngine/processCommand, Message: " + e.getMessage());
        }
    }

}
