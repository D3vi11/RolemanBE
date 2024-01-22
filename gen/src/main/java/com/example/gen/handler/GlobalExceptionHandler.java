package com.example.gen.handler;

import com.example.gen.ResponseObject;
import com.example.gen.exception.EnemiesNotFoundException;
import com.google.gson.JsonParseException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EnemiesNotFoundException.class)
    public ResponseEntity<ResponseObject> enemiesNotFound(EnemiesNotFoundException e){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ResponseObject(e.getMessage(),HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<ResponseObject> jsonParse(JsonParseException e){
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
