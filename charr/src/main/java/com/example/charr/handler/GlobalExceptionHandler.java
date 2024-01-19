package com.example.charr.handler;

import com.example.charr.ResponseObject;
import com.example.charr.exception.NotFoundException;
import com.example.charr.exception.UnableToSaveException;
import com.example.charr.exception.UnableToDeleteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnableToSaveException.class)
    public ResponseEntity<ResponseObject> unableToCreate(UnableToSaveException e){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseObject(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR));
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseObject> notFound(NotFoundException e){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ResponseObject(e.getMessage(),HttpStatus.NOT_FOUND));
    }
    @ExceptionHandler(UnableToDeleteException.class)
    public ResponseEntity<ResponseObject> deletionError(UnableToDeleteException e){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseObject(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
