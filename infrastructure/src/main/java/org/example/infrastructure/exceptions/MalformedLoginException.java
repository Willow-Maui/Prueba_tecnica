package org.example.infrastructure.exceptions;

import org.springframework.security.core.AuthenticationException;

public class MalformedLoginException extends AuthenticationException {
    public MalformedLoginException(String message, Throwable cause) {
        super(message, cause);
    }
}