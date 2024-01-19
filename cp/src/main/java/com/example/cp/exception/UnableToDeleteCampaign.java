package com.example.cp.exception;

public class UnableToDeleteCampaign extends RuntimeException{
    public UnableToDeleteCampaign(String message){
        super(message);
    }
}
