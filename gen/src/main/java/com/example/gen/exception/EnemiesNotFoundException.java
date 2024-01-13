package com.example.gen.exception;

public class EnemiesNotFoundException extends RuntimeException{
    public EnemiesNotFoundException(String message){
        super(message);
    }
}
