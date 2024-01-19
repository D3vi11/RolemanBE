package com.example.wth.exception;

public class UnableToSaveException extends RuntimeException{
    public UnableToSaveException(String message){
        super(message);
    }
}
