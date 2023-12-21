package com.example.data.handler;

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
    public ResponseEntity<String> nothingFound(NothingFoundException e){
        JSONObject jsonObject = prepareObject(e,HttpStatus.OK);
        return new ResponseEntity<>(jsonObject.toString(),HttpStatus.OK);
    }

    @ExceptionHandler(FailedToSaveException.class)
    public ResponseEntity<String> failedSave(FailedToSaveException e){
        JSONObject jsonObject = prepareObject(e,HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(jsonObject.toString(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FailedToDeleteException.class)
    public ResponseEntity<String> failedDelete(FailedToDeleteException e){
        JSONObject jsonObject = prepareObject(e,HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(jsonObject.toString(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private JSONObject prepareObject(RuntimeException e, HttpStatus status){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", status);
        jsonObject.put("message", e.getMessage());
        return jsonObject;
    }
}
