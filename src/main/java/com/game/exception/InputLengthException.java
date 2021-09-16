package com.game.exception;

public class InputLengthException extends RuntimeException{
    private String errorMessage;


    public String getErrorMessage(){
        return "input words length Exception";
    }
}
