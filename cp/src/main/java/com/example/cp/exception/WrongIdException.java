package com.example.cp.exception;

public class WrongIdException extends RuntimeException{
    public WrongIdException(String message){
        super(message);
    }
}
