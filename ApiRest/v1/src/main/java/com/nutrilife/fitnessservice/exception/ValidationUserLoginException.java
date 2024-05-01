package com.nutrilife.fitnessservice.exception;

public class ValidationUserLoginException extends RuntimeException{
    
    public ValidationUserLoginException() {

    }

    public ValidationUserLoginException(String message){
        super(message);
    }

}
