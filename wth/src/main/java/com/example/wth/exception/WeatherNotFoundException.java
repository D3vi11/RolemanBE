package com.example.wth.exception;

public class WeatherNotFoundException extends RuntimeException{

    public WeatherNotFoundException(String message){
        super(message);
    }
}
