package org.example.infrastructure.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@Schema(description = "Respuesta del precio")
public class PriceResponse implements PriceResponseInterface{

    @Schema(description = "ID de la marca", example = "1")
    private Long brandId;

    @Schema(description = "Fecha de inicio", example = "2023-01-01T00:00:00.000+00:00")
    private Date startDate;

    @Schema(description = "Fecha de fin", example = "2023-12-31T23:59:59.000+00:00")
    private Date endDate;

    @Schema(description = "ID de la lista de precios", example = "1")
    private Long priceList;

    @Schema(description = "ID del producto", example = "35455")
    private Long productId;

    @Schema(description = "Prioridad", example = "0")
    private Integer priority;

    @Schema(description = "Precio", example = "35.50")
    private BigDecimal price;

    @Schema(description = "Moneda", example = "EUR")
    private String curr;
}