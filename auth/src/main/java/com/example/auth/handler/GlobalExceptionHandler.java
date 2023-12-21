package com.example.auth.handler;

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
    public ResponseEntity<String> registration(IncorrectRegistrationException e){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",HttpStatus.BAD_REQUEST);
        jsonObject.put("error",e.getMessage());
        return new ResponseEntity<>(jsonObject.toString(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IncorrectConfirmationException.class)
    public ResponseEntity<String> confirmation(IncorrectConfirmationException e){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",HttpStatus.UNPROCESSABLE_ENTITY);
        jsonObject.put("error",e.getMessage());
        return new ResponseEntity<>(jsonObject.toString(),HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(IncorrectLoginException.class)
    public ResponseEntity<String> confirmation(IncorrectLoginException e){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",HttpStatus.BAD_REQUEST);
        jsonObject.put("error",e.getMessage());
        return new ResponseEntity<>(jsonObject.toString(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<String> userExists(UserExistsException e){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",HttpStatus.UNPROCESSABLE_ENTITY);
        jsonObject.put("error",e.getMessage());
        return new ResponseEntity<>(jsonObject.toString(),HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
