package org.example.infrastructure.controllers.exceptions;

import org.example.infrastructure.controllers.constants.ResponseStringConstants;
import org.example.infrastructure.dtos.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * GlobalExceptionHandler is a centralized exception handler for the application.
 * It provides specific handling for exceptions that are commonly encountered.
 *
 * @since 1.0.0
 * @author Willow Maui Garcia
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final String NOT_FOUND_ROUTE_ERROR_MESSAGE = "Ruta de controlador no encontrada: {}.";

/**
 * Handles NoHandlerFoundException by logging the error and returning a NOT_FOUND response.
 *
 * <p>This method is invoked when a requested URL does not have a corresponding handler.
 * It logs the error and constructs a ResponseEntity with an ErrorResponse indicating
 * that the requested URL is not found.
 *
 * @param ex the NoHandlerFoundException thrown when no handler is found for a request
 * @return a ResponseEntity containing an ErrorResponse with a NOT_FOUND status
 * @since 1.0.0
 * @author Willow Maui Garcia
 */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        logger.error(NOT_FOUND_ROUTE_ERROR_MESSAGE, ex.getRequestURL() );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder().error(ResponseStringConstants.UNKNOWN_URL).build());
    }
    /**
     * Handles NoResourceFoundException by logging the error and returning a NOT_FOUND response.
     *
     * <p>This method is invoked when a requested URL does not have a corresponding handler.
     * It logs the error and constructs a ResponseEntity with an ErrorResponse indicating
     * that the requested URL is not found.
     *
     * @param ex the NoResourceFoundException thrown when no handler is found for a request
     * @return a ResponseEntity containing an ErrorResponse with a NOT_FOUND status
     * @since 1.0.0
     * @author Willow Maui Garcia
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFoundException(NoResourceFoundException ex) {
        logger.error(NOT_FOUND_ROUTE_ERROR_MESSAGE, ex.getResourcePath() );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder().error(ResponseStringConstants.UNKNOWN_URL).build());
    }
}