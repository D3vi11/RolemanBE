package com.example.eq.exception;

public class UnableToDeleteException extends RuntimeException{
    public UnableToDeleteException(String message){
        super(message);
    }
}
