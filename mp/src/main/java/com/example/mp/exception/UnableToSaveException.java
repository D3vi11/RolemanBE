package com.example.mp.exception;

public class UnableToSaveException extends RuntimeException{
    public UnableToSaveException(String message){
        super(message);
    }
}
