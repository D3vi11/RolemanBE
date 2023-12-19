package com.example.auth.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class IncorrectRegistrationException extends RuntimeException{
    public IncorrectRegistrationException(String message){
        super(message);
    }
}
