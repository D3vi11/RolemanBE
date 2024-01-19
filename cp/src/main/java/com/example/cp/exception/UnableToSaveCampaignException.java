package com.example.cp.exception;

public class UnableToSaveCampaignException extends RuntimeException{
    public UnableToSaveCampaignException(String message){
        super(message);
    }
}
