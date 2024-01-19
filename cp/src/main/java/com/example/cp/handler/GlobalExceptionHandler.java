package com.example.cp.handler;

import com.example.cp.ResponseObject;
import com.example.cp.exception.CampaignNotFoundException;
import com.example.cp.exception.UnableToDeleteCampaign;
import com.example.cp.exception.UnableToSaveCampaignException;
import com.example.cp.exception.WrongIdException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CampaignNotFoundException.class)
    public ResponseEntity<ResponseObject> campaignNotFound(CampaignNotFoundException e){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ResponseObject(e.getMessage(),HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(UnableToSaveCampaignException.class)
    public ResponseEntity<ResponseObject> errorSavingCampaign(UnableToSaveCampaignException e){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseObject(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(WrongIdException.class)
    public ResponseEntity<ResponseObject> wrongCampaignId(WrongIdException e){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseObject(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(UnableToDeleteCampaign.class)
    public ResponseEntity<ResponseObject> errorDeletingCampaign(UnableToDeleteCampaign e){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseObject(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
