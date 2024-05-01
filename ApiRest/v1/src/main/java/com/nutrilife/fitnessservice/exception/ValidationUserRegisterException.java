package com.nutrilife.fitnessservice.exception;

public class ValidationUserRegisterException extends RuntimeException{
    
    public ValidationUserRegisterException() {

    }

    public ValidationUserRegisterException(String message) {
        super(message);
    }
}
