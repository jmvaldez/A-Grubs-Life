package com.game.client;

import com.game.controller.Game;

import java.io.IOException;

public class Client {
    public static void main(String[] args) throws IOException {

        Game game = new Game();
        game.start();
    }
}
