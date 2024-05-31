package com.nutrilife.fitnessservice.exception;

public class TrainingNotFoundException extends RuntimeException {

    public TrainingNotFoundException() {
        super();
    }

    public TrainingNotFoundException(String message) {
        super(message);
    }
}