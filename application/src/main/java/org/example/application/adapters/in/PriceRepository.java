package org.example.application.adapters.in;

import org.example.domain.models.Price;

import java.util.Date;
/**
 * Interface that defines the methods to interact with the Price object from the
 * application layer.
 */
public interface PriceRepository {
/**
 * This method looks for a price in the database according to the parameters passed.
 * @param queryDate date of the price query.
 * @param productId id of the product.
 * @param brandId id of the brand.
 * @return the price found.
 * @since 1.0.0
 * @author Willow Maui García
 */
Price findPriceByCriteria(Date queryDate, Long productId, Long brandId);
}