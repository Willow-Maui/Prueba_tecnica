package org.example.infrastructure.controllers.exceptions;

import org.example.infrastructure.controllers.PriceController;
import org.example.infrastructure.controllers.constants.ResponseStringConstants;
import org.example.infrastructure.dtos.response.PriceErrorResponse;
import org.example.infrastructure.dtos.response.PriceResponseInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackageClasses = PriceController.class)
public class PriceExceptionHandlerController {

    public static final String TRAZA_DE_LA_EXCEPCION = "Traza de la excepción:";
    public static final String ILLEGAL_ARGUMENT_EXCEPTION_CAPTURADA = "IllegalArgumentException capturada: {}";
    private static final Logger logger = LoggerFactory.getLogger(PriceExceptionHandlerController.class);
    public static final String EXCEPTION_CAPTURADA = "Exception capturada: {}";
    public static final String RUNTIME_EXCEPTION_CAPTURADA = "RuntimeException capturada: {}";

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<PriceResponseInterface> handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.error(ILLEGAL_ARGUMENT_EXCEPTION_CAPTURADA, ex.getMessage());
        logger.debug(TRAZA_DE_LA_EXCEPCION, ex);
        return ResponseEntity.badRequest().body(PriceErrorResponse.builder().error(ResponseStringConstants.PARAMETROS_INVALIDOS + ex.getMessage()).build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<PriceResponseInterface> handleGeneralException(Exception ex) {
        logger.error(EXCEPTION_CAPTURADA, ex.getMessage());
        logger.debug(TRAZA_DE_LA_EXCEPCION, ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(PriceErrorResponse.builder().error(ResponseStringConstants.ERROR_INTERNO + ex.getMessage()).build());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<PriceResponseInterface> handleRuntimeException(RuntimeException ex) {
        logger.error(RUNTIME_EXCEPTION_CAPTURADA, ex.getMessage());
        logger.debug(TRAZA_DE_LA_EXCEPCION, ex);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(PriceErrorResponse.builder().error(ResponseStringConstants.ERROR_RUNTIME + ex.getMessage()).build());
    }
}