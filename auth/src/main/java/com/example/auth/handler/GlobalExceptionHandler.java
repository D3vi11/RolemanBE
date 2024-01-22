package com.example.auth.handler;

import com.example.auth.ResponseObject;
import com.example.auth.exception.IncorrectConfirmationException;
import com.example.auth.exception.IncorrectLoginException;
import com.example.auth.exception.IncorrectRegistrationException;
import com.example.auth.exception.UserExistsException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IncorrectRegistrationException.class)
    public ResponseEntity<ResponseObject> registration(IncorrectRegistrationException e){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ResponseObject(e.getMessage(),HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(IncorrectConfirmationException.class)
    public ResponseEntity<ResponseObject> confirmation(IncorrectConfirmationException e){
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ResponseObject(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY));
    }

    @ExceptionHandler(IncorrectLoginException.class)
    public ResponseEntity<ResponseObject> confirmation(IncorrectLoginException e){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ResponseObject(e.getMessage(),HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<ResponseObject> userExists(UserExistsException e){
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ResponseObject(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY));
    }
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ResponseObject> nullPointer(NullPointerException e){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseObject(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
