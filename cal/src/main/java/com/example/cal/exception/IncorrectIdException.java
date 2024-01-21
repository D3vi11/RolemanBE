package com.example.cal.exception;

public class IncorrectIdException extends RuntimeException{
    public IncorrectIdException(String message){
        super(message);
    }
}
