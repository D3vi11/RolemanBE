package com.example.mp.exception;

public class UnableToDeleteException extends RuntimeException{
    public UnableToDeleteException(String message){
        super(message);
    }
}
