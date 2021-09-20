package com.game.exception;

public class DeadPlayerInputException extends RuntimeException {
    private String errorMessage;


    public String getErrorMessage() {
        return "DeadPlayer Input Exception";
    }
}
