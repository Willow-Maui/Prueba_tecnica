package org.example.infrastructure.dtos.request;

import lombok.Data;

import java.util.Date;

@Data
public class PriceRequest {

    private Date fechaConsulta;

    private Long productId;

    private Long brandId;
}