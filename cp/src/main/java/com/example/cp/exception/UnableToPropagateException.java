package com.example.cp.exception;

public class UnableToPropagateException extends RuntimeException{
    public UnableToPropagateException(String message){
        super(message);
    }
}
