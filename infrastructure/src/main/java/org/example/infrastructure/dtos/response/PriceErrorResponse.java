package org.example.infrastructure.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class to represent the error response of the price controller when the price is not found, or
 * when there is an error in the request parameters.
 *
 * @author Willow Maui García
 * @since 1.0.0
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceErrorResponse implements PriceResponseInterface{
    private String error;
}
