package com.example.wth.handler;

import com.example.wth.exception.CampaignNotFoundException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CampaignNotFoundException.class)
    public ResponseEntity<String> campaignDoesntExist(CampaignNotFoundException e){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status",HttpStatus.NOT_FOUND);
        jsonObject.put("error",e.getMessage());
        return new ResponseEntity<>(jsonObject.toString(), HttpStatus.NOT_FOUND);
    }
}
