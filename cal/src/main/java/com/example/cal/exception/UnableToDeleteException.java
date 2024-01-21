package com.example.cal.exception;

public class UnableToDeleteException extends RuntimeException{
    public UnableToDeleteException(String message){
        super(message);
    }
}
