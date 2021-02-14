package com.spring.project.exceptions;

public class CategoryAlreadyExistException extends RuntimeException {
    public CategoryAlreadyExistException() {
    }

    public CategoryAlreadyExistException(String message) {
        super(message);
    }
}
