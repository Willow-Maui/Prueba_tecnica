package org.example.infrastructure.adapters.primary.dtos.response;


/**
 * Class to represent the error response of the price controller when the price is not found, or
 * when there is an error in the request parameters.
 *
 * @author Willow Maui García
 * @since 1.0.0
 */

public record PriceErrorResponse(String error) implements PriceResponseInterface{
}
