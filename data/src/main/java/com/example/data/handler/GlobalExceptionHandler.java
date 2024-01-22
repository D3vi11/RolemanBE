package com.example.data.handler;

import com.example.data.ResponseObject;
import com.example.data.exception.FailedToDeleteException;
import com.example.data.exception.FailedToSaveException;
import com.example.data.exception.NothingFoundException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NothingFoundException.class)
    public ResponseEntity<ResponseObject> nothingFound(NothingFoundException e){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ResponseObject(e.getMessage(),HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(FailedToSaveException.class)
    public ResponseEntity<ResponseObject> failedSave(FailedToSaveException e){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseObject(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(FailedToDeleteException.class)
    public ResponseEntity<ResponseObject> failedDelete(FailedToDeleteException e){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseObject(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR));
    }
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ResponseObject> nullPointer(NullPointerException e){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseObject(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
