package com.example.auth.handler;

import org.json.JSONObject;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalValidationHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> validation(MethodArgumentNotValidException e){
        String errorMessage = e.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("\n"));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",HttpStatus.BAD_REQUEST);
        jsonObject.put("error",errorMessage);
        return new ResponseEntity<>(jsonObject.toString(), HttpStatus.BAD_REQUEST);
    }
}
