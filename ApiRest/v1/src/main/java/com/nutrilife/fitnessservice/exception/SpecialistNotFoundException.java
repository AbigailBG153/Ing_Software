package com.nutrilife.fitnessservice.exception;

public class SpecialistNotFoundException extends RuntimeException {
    
    public SpecialistNotFoundException() {
        super();
    }

    public SpecialistNotFoundException(String message) {
        super(message);
    }
}
