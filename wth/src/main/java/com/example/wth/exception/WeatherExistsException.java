package com.example.wth.exception;

public class WeatherExistsException extends RuntimeException{
    public WeatherExistsException(String message){
        super(message);
    }
}
