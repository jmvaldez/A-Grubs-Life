package com.game.exception;

public class LivePlayerInputException extends RuntimeException {
    private String errorMessage;


    public String getErrorMessage() {
        return "input words length Exception";
    }
}
