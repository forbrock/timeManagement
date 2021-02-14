package com.spring.project.exceptions;

public class TimeIsNotValidException extends RuntimeException {
    public TimeIsNotValidException() {
        super();
    }

    public TimeIsNotValidException(String message) {
        super(message);
    }
}
