package org.example.domain.services;

import org.example.domain.models.Price;
import org.example.domain.models.querys.PriceQuery;

public interface PriceService {
    Price getPriceByCriteria(PriceQuery criteria);
}
