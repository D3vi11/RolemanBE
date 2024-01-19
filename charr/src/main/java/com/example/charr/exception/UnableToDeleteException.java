package com.example.charr.exception;

public class UnableToDeleteException extends RuntimeException{
    public UnableToDeleteException(String message){
        super(message);
    }
}
