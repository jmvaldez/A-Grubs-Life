package com.game.model.engine;

import com.game.backupclass.KeyWordIdentifier;
import com.game.controller.Game;

import java.util.ArrayList;

public class LogicEngine {
    private CommandProcessor commandProcessor;
    private com.game.backupclass.KeyWordIdentifier keyWordIdentifier;
    private TextParser textParser;
    public LogicEngine(){
        this.textParser = new TextParser();
        this.keyWordIdentifier = new KeyWordIdentifier();
        this.commandProcessor = new CommandProcessor();
    }

    public void processCommand(String userInput){//
        try{
        ArrayList<String> parsedInput = textParser.parseInput(userInput);
//        ArrayList command = keyWordIdentifier.identifyKewWords(parsedInput);
        commandProcessor.executeCommand(parsedInput);}
        catch(Exception e){
            Game.caterpillar.setLastAction("I can't process that, try again with a verb/noun combo of relevant game objects.");
        }
    }

}
