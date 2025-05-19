package org.example.infrastructure.dtos.response;

/**
 * DTO with the error response of the controller.
 *
 * @since 1.0.0
 * @author Willow Maui García
 */
public record ErrorResponse(String error)implements PriceResponseInterface {
}
