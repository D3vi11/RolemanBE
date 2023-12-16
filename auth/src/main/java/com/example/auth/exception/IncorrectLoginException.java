package com.example.auth.exception;

public class IncorrectLoginException extends RuntimeException{
    public IncorrectLoginException(String message){
        super(message);
    }
}
