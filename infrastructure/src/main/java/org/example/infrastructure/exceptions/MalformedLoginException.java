package org.example.infrastructure.exceptions;

import org.springframework.security.core.AuthenticationException;
/**
 * Exception thrown when a login attempt is malformed.
 *
 * <p>This exception is used to indicate that the login data received during
 * authentication is not in the expected format or is otherwise malformed.
 * It extends {@link AuthenticationException} to integrate with Spring Security
 * authentication processes.</p>
 *
 * @since 1.0.0
 * @author Willow Maui García
 */
public class MalformedLoginException extends AuthenticationException {
    public MalformedLoginException(String message, Throwable cause) {
        super(message, cause);
    }
}