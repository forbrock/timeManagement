package com.spring.project.exceptions;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class CredentialsException extends RuntimeException{
    private final String reason;
}
