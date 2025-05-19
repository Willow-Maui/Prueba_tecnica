package org.example.infrastructure.adapters.primary.dtos.request;

import lombok.Data;

import java.util.Date;

/**
 * Represents a request to obtain price information based on specific criteria.
 * Contains fields to specify the query date, product ID, and brand ID.
 *
 * @since 1.0
 * @author Willow Maui Garcia
 */
@Data
public class PriceRequest {

    private Date queryDate;

    private Long productId;

    private Long brandId;
}