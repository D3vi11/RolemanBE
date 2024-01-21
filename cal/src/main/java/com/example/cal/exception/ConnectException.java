package com.example.cal.exception;

public class ConnectException extends RuntimeException{
    public ConnectException(String message){
        super(message);
    }
}
