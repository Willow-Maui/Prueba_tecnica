package org.example.domain.models.querys;

import lombok.Data;

import java.util.Date;
/**
 * Represents a query for retrieving price information based on specific criteria.
 * Contains fields to specify the query date, product ID, and brand ID.
 *
 * @since 1.0.0
 * @author Willow Maui Garcia
 */
@Data
public class PriceQuery {
    private Date queryDate;

    private Long productId;

    private Long brandId;
}
