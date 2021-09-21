package com.game.exception;

public class OddsFunctionException extends RuntimeException {
    private String errorMessage;


    public String getErrorMessage() {
        return "inValid Odds Exception";
    }
}
