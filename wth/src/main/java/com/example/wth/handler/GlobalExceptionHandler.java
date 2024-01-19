package com.example.wth.handler;

import com.example.wth.ResponseObject;
import com.example.wth.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PreferenceNotFoundException.class)
    public ResponseEntity<ResponseObject> preferenceDoesntExist(PreferenceNotFoundException e){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ResponseObject(e.getMessage(),HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(WeatherNotFoundException.class)
    public ResponseEntity<ResponseObject> weatherNotFound(WeatherNotFoundException e){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ResponseObject(e.getMessage(),HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(PreferenceAlreadyExistsException.class)
    public ResponseEntity<ResponseObject> preferenceExists(PreferenceAlreadyExistsException e){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseObject(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(UnableToDeleteException.class)
    public ResponseEntity<ResponseObject> preferenceDeletionError(UnableToDeleteException e){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseObject(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(IdDoesntMatchException.class)
    public ResponseEntity<ResponseObject> idDoesntMatch(IdDoesntMatchException e){
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ResponseObject(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY));
    }
    @ExceptionHandler(WeatherExistsException.class)
    public ResponseEntity<ResponseObject> weatherExists(WeatherExistsException e){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseObject(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR));
    }
    @ExceptionHandler(UnableToSaveException.class)
    public ResponseEntity<ResponseObject> saveError(UnableToSaveException e){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseObject(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
