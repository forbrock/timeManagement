package com.spring.project.exceptions;

public class CategoryAlreadyExistException extends Exception {
    public CategoryAlreadyExistException() {
    }

    public CategoryAlreadyExistException(String message) {
        super(message);
    }
}
