package com.game.client;

import com.game.controller.Game;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Client {
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        Game game = new Game();
        game.start();
    }
}
