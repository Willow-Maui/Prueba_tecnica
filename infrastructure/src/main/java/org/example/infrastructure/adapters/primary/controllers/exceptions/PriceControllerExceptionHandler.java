package org.example.infrastructure.adapters.primary.controllers.exceptions;

import org.example.infrastructure.adapters.primary.controllers.PriceController;
import org.example.infrastructure.adapters.primary.controllers.constants.ResponseStringConstants;
import org.example.infrastructure.adapters.primary.dtos.response.PriceErrorResponse;
import org.example.infrastructure.adapters.primary.dtos.response.PriceResponseInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
/**
 * PriceControllerExceptionHandler is a centralized exception handler for the PriceController.
 * It provides specific handling for exceptions that are commonly encountered.
 *
 * @since 1.0.0
 * @author Willow Maui Garcia
 */
@ControllerAdvice(basePackageClasses = PriceController.class)
public class PriceControllerExceptionHandler {

    private static final String EXCEPTION_STACK_TRACE = "Traza de la excepción:";
    private static final String ILLEGAL_ARGUMENT_EXCEPTION_CATCH = "IllegalArgumentException capturada: {}";
    private static final Logger logger = LoggerFactory.getLogger(PriceControllerExceptionHandler.class);
    private static final String EXCEPTION_CATCH = "Exception capturada: {}";
    private static final String RUNTIME_EXCEPTION_CATCH = "RuntimeException capturada: {}";
    /**
     * Handles IllegalArgumentException by logging the error and returning a INVALID_PARAMETERS response.
     *
     * @param ex the IllegalArgumentException thrown when no handler is found for a request
     * @return a ResponseEntity containing an ErrorResponse with a INVALID_PARAMETERS status
     * @since 1.0.0
     * @author Willow Maui Garcia
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<PriceResponseInterface> handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.error(ILLEGAL_ARGUMENT_EXCEPTION_CATCH, ex.getMessage());
        logger.debug(EXCEPTION_STACK_TRACE, ex);
        return ResponseEntity.badRequest().body(new PriceErrorResponse(ResponseStringConstants.INVALID_PARAMETERS + ex.getMessage()));
    }
    /**
     * Handles Exception by logging the error and returning a INTERNAL_SERVER_ERROR response.
     *
     * @param ex the Exception thrown when no handler is found for a request
     * @return a ResponseEntity containing an ErrorResponse with a INTERNAL_SERVER_ERROR status
     * @since 1.0.0
     * @author Willow Maui Garcia
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<PriceResponseInterface> handleGeneralException(Exception ex) {
        logger.error(EXCEPTION_CATCH, ex.getMessage());
        logger.debug(EXCEPTION_STACK_TRACE, ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new PriceErrorResponse(ResponseStringConstants.INTERNAL_ERROR + ex.getMessage()));
    }
    /**
     * Handles RuntimeException by logging the error and returning a SERVICE_UNAVAILABLE response.
     *
     * @param ex the RuntimeException thrown when no handler is found for a request
     * @return a ResponseEntity containing an ErrorResponse with a SERVICE_UNAVAILABLE status
     * @since 1.0.0
     * @author Willow Maui Garcia
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<PriceResponseInterface> handleRuntimeException(RuntimeException ex) {
        logger.error(RUNTIME_EXCEPTION_CATCH, ex.getMessage());
        logger.debug(EXCEPTION_STACK_TRACE, ex);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new PriceErrorResponse(ResponseStringConstants.ERROR_RUNTIME + ex.getMessage()));
    }
}