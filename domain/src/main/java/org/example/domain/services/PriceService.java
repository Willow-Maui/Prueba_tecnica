package org.example.domain.services;

import org.example.domain.models.Price;
import org.example.domain.models.querys.PriceQuery;

public interface PriceService {
    public Price getPriceByCriteria(PriceQuery criteria);
}
