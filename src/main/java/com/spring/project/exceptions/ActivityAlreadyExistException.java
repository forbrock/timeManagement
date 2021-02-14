package com.spring.project.exceptions;

public class ActivityAlreadyExistException extends RuntimeException {
    public ActivityAlreadyExistException() {
        super();
    }

    public ActivityAlreadyExistException(String message) {
        super(message);
    }
}
