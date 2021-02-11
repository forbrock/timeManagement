package com.spring.project.exceptions;

public class ActivityAlreadyExistException extends Exception {
    public ActivityAlreadyExistException() {}

    public ActivityAlreadyExistException(String message) {
        super(message);
    }
}
