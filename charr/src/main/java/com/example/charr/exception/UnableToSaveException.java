package com.example.charr.exception;

public class UnableToSaveException extends RuntimeException{
    public UnableToSaveException(String message){
        super(message);
    }
}
