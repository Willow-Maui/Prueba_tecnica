package org.example.infrastructure.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.example.domain.services.PriceService;
import org.example.infrastructure.aspects.TimeMeasurement;
import org.example.infrastructure.aspects.UserLog;
import org.example.infrastructure.controllers.constants.APIConstants;
import org.example.infrastructure.controllers.constants.ResponseStringConstants;
import org.example.infrastructure.dtos.request.PriceRequest;
import org.example.infrastructure.dtos.response.PriceErrorResponse;
import org.example.infrastructure.dtos.response.PriceResponse;
import org.example.infrastructure.dtos.response.PriceResponseInterface;
import org.example.infrastructure.mappers.PriceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * This class is a controller for the price resource.
 *
 * @since 1.0.0
 * @author Willow Maui Garcia
 */
@RestController
@RequestMapping(APIConstants.API_PRICES)
@RequiredArgsConstructor
public class PriceController {

    private final PriceService priceService;
    private final PriceMapper priceMapper;
    private static final Logger logger = LoggerFactory.getLogger(PriceController.class);

    /**
     * Retrieves the price that matches the given search criteria, such as query date, product ID, and brand ID.
     * If the query date is not provided, it searches for the current valid price.
     *
     * @param priceRequest The request containing the criteria for retrieving the price.
     * @return A ResponseEntity containing the price details if found, or an error response if not found or if any issue occurs.
     *
     * @apiNote This endpoint requires the "ROLE_PRUEBA" authority to access.
     *
     * @since 1.0.0
     * @author Willow Maui Garcia
     */
    @PostMapping(value="/",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Obtener el precio según los criterios", description = "Obtiene el precio que cumple con los criterios de busqueda, pudiendo ser nulo el de fecha.")
    @ApiResponse(responseCode = "200", description = "Precio encontrado", content = @Content(schema = @Schema(implementation = PriceResponse.class)))
    @ApiResponse(responseCode = "400", description = "Parámetros de solicitud inválidos", content = @Content(schema = @Schema(example = "{\"error\": \""+ ResponseStringConstants.INVALID_PARAMETERS +"<mensaje de error específico.>\"}")))
    @ApiResponse(responseCode = "404", description = "Precio no encontrado", content = @Content(schema = @Schema(example = "{\"error\": \""+ ResponseStringConstants.PRICE_NOT_FOUND +"\"}")))
    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(schema = @Schema(example = "{\"error\": \""+ ResponseStringConstants.INTERNAL_ERROR +"<mensaje de error específico>.\"}")))
    @ApiResponse(responseCode = "503", description = "Error de tiempo de ejecución", content = @Content(schema = @Schema(example = "{\"error\": \""+ ResponseStringConstants.ERROR_RUNTIME+"<mensaje de error específico>.\"}")))
    @Secured("ROLE_PRUEBA")
    @UserLog
    @TimeMeasurement
    public ResponseEntity<PriceResponseInterface> getPrice(@RequestBody PriceRequest priceRequest) {
        logger.info("getPrice llamado con PriceRequest: {}", priceRequest);
        PriceResponse priceResponse = priceMapper.priceToPriceResponse(priceService.getPriceByCriteria(priceMapper.requestToQuery(priceRequest)));
        logger.debug("PriceResponse obtenido: {}", priceResponse);
        if (Objects.isNull(priceResponse)) {
            logger.warn("PriceResponse es nulo, devolviendo NOT_FOUND");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new PriceErrorResponse(ResponseStringConstants.PRICE_NOT_FOUND));
        }
        logger.info("PriceResponse encontrado, devolviendo OK");
        return ResponseEntity.ok(priceResponse);
    }

}