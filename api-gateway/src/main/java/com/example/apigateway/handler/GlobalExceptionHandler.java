package com.example.apigateway.handler;

import com.example.apigateway.exception.IncorrectTokenException;
import jakarta.ws.rs.core.Response;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IncorrectTokenException.class)
    public ResponseEntity<String> incorrectToken(IncorrectTokenException e){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", HttpStatus.UNAUTHORIZED);
        jsonObject.put("error",e.getMessage());
        return new ResponseEntity<>(jsonObject.toString(),HttpStatus.UNAUTHORIZED);
    }
}
