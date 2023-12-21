package com.example.data.exception;

public class FailedToSaveException extends RuntimeException{
    public FailedToSaveException(String message){
        super(message);
    }
}
