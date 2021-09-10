package com.game.model.materials;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Ant {

    private String name;

    public Ant () {
        this.name = getRandomAnt();
    }

    private String getRandomAnt() {

        List<String> ants = new ArrayList<>(Arrays.asList("ant A","ant B", "ant C", "ant D"));

        Random random = new Random();
        int randomAnt = random.nextInt(ants.size());
        String ant = ants.get(randomAnt);

        return ant;
    }

    public String getName() {
        return name;
    }
}