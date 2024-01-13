package com.example.gen.handler;

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
    public ResponseEntity<String> enemiesNotFound(EnemiesNotFoundException e){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", HttpStatus.NOT_FOUND);
        jsonObject.put("error", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(jsonObject.toString());
    }

    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<String> jsonParse(JsonParseException e){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
        jsonObject.put("error", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(jsonObject.toString());
    }
}
