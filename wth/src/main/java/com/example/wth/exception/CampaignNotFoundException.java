package com.example.wth.exception;

public class CampaignNotFoundException extends RuntimeException{

    public CampaignNotFoundException(String message){
        super(message);
    }
}
