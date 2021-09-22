package com.game.model.engine;

import com.game.model.materials.Caterpillar;
import com.game.model.materials.Enemy;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CommandProcessorTest {


    @Before
    public void setup(){
        Caterpillar testingCat = new Caterpillar();
        Enemy testingEnemyBoss = new Enemy("boss", 100, 100, 100);

    }

    @Test
    public void executeCommand() {
    }
}
