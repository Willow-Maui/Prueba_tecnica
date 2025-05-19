package org.example.infrastructure.adapters.primary.controllers.constants;
/**
 * Constants class with the strings used in the responses of the controllers.
 *
 * @since 1.0.0
 * @author Willow Maui Garcia
 */
public class ResponseStringConstants {
    private ResponseStringConstants(){}

    public static final String INVALID_PARAMETERS ="Parámetros de solicitud inválidos: ";
    public static final String PRICE_NOT_FOUND ="No se encontró el precio para los criterios proporcionados.";
    public static final String INTERNAL_ERROR ="Error interno del servidor: ";
    public static final String ERROR_RUNTIME="Error runtime del servidor: ";
    public static final String UNKNOWN_URL = "La URL solicitada no está contemplada en el programa.";
}
