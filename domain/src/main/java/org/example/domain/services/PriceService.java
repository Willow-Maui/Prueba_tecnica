package org.example.domain.services;

import org.example.domain.models.Price;
import org.example.domain.models.querys.PriceQuery;
/**
 * Service interface for handling price-related operations.
 *
 * @since 1.0.0
 * @author Willow Maui Garcia
 */
public interface PriceService {
    Price getPriceByCriteria(PriceQuery criteria);
}
