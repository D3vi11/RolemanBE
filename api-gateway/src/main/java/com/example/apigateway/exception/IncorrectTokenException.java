package com.example.apigateway.exception;

public class IncorrectTokenException extends RuntimeException{
    public IncorrectTokenException(String message){
        super(message);
    }
}
