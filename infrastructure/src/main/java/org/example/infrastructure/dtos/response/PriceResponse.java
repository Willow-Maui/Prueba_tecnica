package org.example.infrastructure.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Response DTO for the price. It contains the fields of the price that are relevant
 * for the client.
 *
 * @since 1.0.0
 * @author Willow Maui Garcia
 */
@Schema(description = "Respuesta del precio")
public record PriceResponse(
        @Schema(description = "ID de la marca", example = "1")
        Long brandId,
        @Schema(description = "Fecha de inicio", example = "2023-01-01T00:00:00.000+00:00")
        Date startDate,
        @Schema(description = "Fecha de fin", example = "2023-12-31T23:59:59.000+00:00")
        Date endDate,
        @Schema(description = "ID de la lista de precios", example = "1")
        Long priceList,
        @Schema(description = "ID del producto", example = "35455")
        Long productId,
        @Schema(description = "Prioridad", example = "0")
        Integer priority,
        @Schema(description = "Precio", example = "35.50")
        BigDecimal price,
        @Schema(description = "Moneda", example = "EUR")
        String curr
) implements PriceResponseInterface {
}
