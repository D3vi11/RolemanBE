package com.example.wth.exception;

public class UnableToDeleteException extends RuntimeException{
    public UnableToDeleteException(String message){
        super(message);
    }
}
