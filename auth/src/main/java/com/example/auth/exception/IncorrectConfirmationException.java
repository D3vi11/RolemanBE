package com.example.auth.exception;

public class IncorrectConfirmationException extends RuntimeException{

    public IncorrectConfirmationException(String message){
        super(message);
    }
}
