package com.example.cal.handler;

import com.example.cal.ResponseObject;
import com.example.cal.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseObject> notFound(NotFoundException e){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ResponseObject(e.getMessage(),HttpStatus.NOT_FOUND));
    }
    @ExceptionHandler(UnableToSaveException.class)
    public ResponseEntity<ResponseObject> unableToSave(UnableToSaveException e){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseObject(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR));
    }
    @ExceptionHandler(UnableToDeleteException.class)
    public ResponseEntity<ResponseObject> unableToDelete(UnableToDeleteException e){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseObject(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR));
    }
    @ExceptionHandler(IncorrectIdException.class)
    public ResponseEntity<ResponseObject> incorrectId(IncorrectIdException e){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ResponseObject(e.getMessage(),HttpStatus.BAD_REQUEST));
    }
    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<ResponseObject> connectError(ConnectException e){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseObject(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
