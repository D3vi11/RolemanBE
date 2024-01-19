package com.example.cp.exception;

public class CampaignNotFoundException extends RuntimeException{
    public CampaignNotFoundException(String message){
        super(message);
    }
}
