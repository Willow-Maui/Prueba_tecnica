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


@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final String MENSAJE_ERROR_RUTA_NO_ENCONTRADA = "Ruta de controlador no encontrada: {}.";

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        logger.error(MENSAJE_ERROR_RUTA_NO_ENCONTRADA, ex.getRequestURL() );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder().error(ResponseStringConstants.URL_DESCONOCIDA).build());
    }
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFoundException(NoResourceFoundException ex) {
        logger.error(MENSAJE_ERROR_RUTA_NO_ENCONTRADA , ex.getResourcePath() );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder().error(ResponseStringConstants.URL_DESCONOCIDA).build());
    }
}