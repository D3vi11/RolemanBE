package com.example.cp.handler;

import com.example.cp.exception.CampaignNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CampaignNotFoundException.class)
    public ResponseEntity<ExceptionEntity> campaignNotFound(CampaignNotFoundException e){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionEntity(HttpStatus.NOT_FOUND,e.getMessage()));
    }
}
