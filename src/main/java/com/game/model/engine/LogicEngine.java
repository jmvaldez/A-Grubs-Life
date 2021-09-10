package com.game.model.engine;

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
        this.commandProcessor = new CommandProcessor(caterpillar, locations, enemies);
    }
    public void processCommand(String userInput){//
        ArrayList parsedInput = textParser.parseInput(userInput);
        ArrayList command = keyWordIdentifier.identifyKewWords(parsedInput);
        commandProcessor.executeCommand(command);
    }



}
