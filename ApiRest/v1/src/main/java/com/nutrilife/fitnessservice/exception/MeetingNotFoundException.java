package com.nutrilife.fitnessservice.exception;

public class MeetingNotFoundException extends RuntimeException{
    public MeetingNotFoundException() {
        super();
    }

    public MeetingNotFoundException(String message) {
        super(message);
    }
}
