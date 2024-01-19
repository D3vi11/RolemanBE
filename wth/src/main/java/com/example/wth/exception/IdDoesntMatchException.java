package com.example.wth.exception;

public class IdDoesntMatchException extends RuntimeException{
    public IdDoesntMatchException(String message){
        super(message);
    }
}
