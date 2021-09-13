package com.game.model.engine;

import com.game.controller.Game;
import com.game.model.materials.Caterpillar;
import com.game.model.materials.Enemy;
import com.game.model.materials.Location;

import java.util.ArrayList;
import java.util.HashMap;

public class LogicEngine {
    private CommandProcessor commandProcessor;
    private KeyWordIdentifier keyWordIdentifier;
    private TextParser textParser;
    public LogicEngine(Caterpillar caterpillar, HashMap<String, Location> locations, HashMap<String, Enemy> enemies){
        setUpEngineComponents(caterpillar,locations, enemies);
    }

    private void setUpEngineComponents(Caterpillar caterpillar, HashMap<String, Location> locations, HashMap<String,Enemy> enemies){
        this.textParser = new TextParser();
        this.keyWordIdentifier = new KeyWordIdentifier();
        this.commandProcessor = new CommandProcessor(locations, enemies);
    }
    public void processCommand(String userInput){//
        try{
        ArrayList parsedInput = textParser.parseInput(userInput);
//        ArrayList command = keyWordIdentifier.identifyKewWords(parsedInput);
        commandProcessor.executeCommand(parsedInput);}
        catch(Exception e){
            Game.caterpillar.setLastAction("I can't process that, try again with a verb/noun combo of relevant game objects.");
        }
    }



}
