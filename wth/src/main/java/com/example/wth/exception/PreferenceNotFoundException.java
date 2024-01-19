package com.example.wth.exception;

public class PreferenceNotFoundException extends RuntimeException{

    public PreferenceNotFoundException(String message){
        super(message);
    }
}
