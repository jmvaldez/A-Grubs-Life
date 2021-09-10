package com.game.model.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;

//grab appropriate command (verb, noun)
public class KeyWordIdentifier {

    HashSet<String> keyWords;
    public KeyWordIdentifier(){
        keyWords = new HashSet<>();
        keyWords.add("GO");
        keyWords.add("NORTH");
        keyWords.add("LEAF");
        keyWords.add("EAT");
        keyWords.add("SOUTH");
        keyWords.add("EAST");
        keyWords.add("WEST");
        keyWords.add("ANT");
        keyWords.add("TAME");
        keyWords.add("HELP");
        keyWords.add("COMBAT");
        keyWords.add("START");
        keyWords.add("GAME");
        keyWords.add("SPIDER");
        keyWords.add("BIRD");
        keyWords.add("FLIES");
        keyWords.add("RAT");
        keyWords.add("SQUIRREL");
        keyWords.add("BEE");
        keyWords.add("ATTACK");
        keyWords.add("GODMODE");
        keyWords.add("CATERPILLAR");
        keyWords.add("HIDE");
    }


    public ArrayList<String> identifyKewWords(ArrayList<String> parsedInput) {
         ArrayList<String> result = new ArrayList();
        if(parsedInput != null){
            for(int i = 0; i < parsedInput.size(); i++){
                if(!keyWords.contains(parsedInput.get(i))){
                    return result;
                }else{
                    result.add(parsedInput.get(i).toUpperCase(Locale.ROOT));
                }
            }
        }

         return result;
    }
}
