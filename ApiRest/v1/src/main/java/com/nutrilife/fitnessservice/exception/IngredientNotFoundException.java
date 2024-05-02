package com.nutrilife.fitnessservice.exception;

public class IngredientNotFoundException extends RuntimeException {

    public IngredientNotFoundException() {
        super();
    }

    public IngredientNotFoundException(String message) {
        super(message);
    }
}
