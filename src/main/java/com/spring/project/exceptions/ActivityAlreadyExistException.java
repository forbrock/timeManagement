package com.spring.project.exceptions;

public class ActivityAlreadyExistException extends Exception {
    public ActivityAlreadyExistException() {
        super();
    }

    public ActivityAlreadyExistException(String message) {
        super(message);
    }
}
