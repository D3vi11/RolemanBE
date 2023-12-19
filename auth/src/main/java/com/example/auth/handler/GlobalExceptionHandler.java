package com.example.auth.handler;

import com.example.auth.exception.IncorrectConfirmationException;
import com.example.auth.exception.IncorrectLoginException;
import com.example.auth.exception.IncorrectRegistrationException;
import com.example.auth.exception.UserExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IncorrectRegistrationException.class)
    public ResponseEntity<String> registration(IncorrectRegistrationException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IncorrectConfirmationException.class)
    public ResponseEntity<String> confirmation(IncorrectConfirmationException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(IncorrectLoginException.class)
    public ResponseEntity<String> confirmation(IncorrectLoginException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<String> userExists(UserExistsException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
