package org.example.application.adapters.in;

import org.example.domain.models.Price;

import java.util.Date;

public interface PriceRepository {
    Price findPriceByCriteria(Date fechaConsulta, Long productId, Long brandId);
}