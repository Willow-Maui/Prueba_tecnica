package org.example.infrastructure.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.example.domain.services.PriceService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping(APIConstants.API_PRICES)
@RequiredArgsConstructor
public class PriceController {

    private final PriceService priceService;
    private final PriceMapper priceMapper;
    private static final Logger logger = LoggerFactory.getLogger(PriceController.class);



    @PostMapping(value="/",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Obtener el precio según los criterios", description = "Obtiene el precio que cumple con los criterios de busqueda, pudiendo ser nulo el de fecha.")
    @ApiResponse(responseCode = "200", description = "Precio encontrado", content = @Content(schema = @Schema(implementation = PriceResponse.class)))
    @ApiResponse(responseCode = "400", description = "Parámetros de solicitud inválidos", content = @Content(schema = @Schema(example = "{\"error\": \""+ ResponseStringConstants.PARAMETROS_INVALIDOS +"<mensaje de error específico.>\"}")))
    @ApiResponse(responseCode = "404", description = "Precio no encontrado", content = @Content(schema = @Schema(example = "{\"error\": \""+ ResponseStringConstants.PRECIO_NO_ENCONTRADO+"\"}")))
    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(schema = @Schema(example = "{\"error\": \""+ ResponseStringConstants.ERROR_INTERNO+"<mensaje de error específico>.\"}")))
    @ApiResponse(responseCode = "503", description = "Error de tiempo de ejecución", content = @Content(schema = @Schema(example = "{\"error\": \""+ ResponseStringConstants.ERROR_RUNTIME+"<mensaje de error específico>.\"}")))
    public ResponseEntity<PriceResponseInterface> getPrice(@RequestBody PriceRequest priceRequest) {
        logger.info("getPrice llamado con PriceRequest: {}", priceRequest);
        PriceResponse priceResponse = priceMapper.priceToPriceResponse(priceService.getPriceByCriteria(priceMapper.requestToQuery(priceRequest)));
        logger.debug("PriceResponse obtenido: {}", priceResponse);
        if (Objects.isNull(priceResponse)) {
            logger.warn("PriceResponse es nulo, devolviendo NOT_FOUND");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PriceErrorResponse.builder().error(ResponseStringConstants.PRECIO_NO_ENCONTRADO).build());
        }
        logger.info("PriceResponse encontrado, devolviendo OK");
        return ResponseEntity.ok(priceResponse);
    }
}