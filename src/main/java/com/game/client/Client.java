package com.game.client;

import com.game.controller.Game;

public class Client {
    public static Game game;

    public static void main(String[] args) {

        Game game = new Game();
        game.start();
    }
}
