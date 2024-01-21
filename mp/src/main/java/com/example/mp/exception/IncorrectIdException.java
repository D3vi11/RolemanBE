package com.example.mp.exception;

public class IncorrectIdException extends RuntimeException{
    public IncorrectIdException(String message){
        super(message);
    }
}
