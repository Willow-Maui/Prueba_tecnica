package org.example.application.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.domain.services.PriceService;
import org.example.domain.models.Price;
import org.example.domain.models.querys.PriceQuery;
import org.example.application.adapters.in.PriceRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;


/**
 * Implementation of the price service
 *
 * @since 1.0.0
 * @author Willow Maui Garcia
 */
@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService{

    private static final String BRAND_ID_VACIO = "Brand id vacío.";

    private static final String PRODUCT_ID_VACIO = "Product id vacío.";

    private final PriceRepository priceRepository;

    /**
     * Retrieves the price based on the given criteria.
     *
     * @param criteria the criteria containing query date, product id, and brand id.
     * @return the price that matches the criteria.
     * @throws IllegalArgumentException if product id or brand id is null.
     *
     * @since 1.0.0
     * @author Willow Maui Garcia
     */
    @Override
    @Transactional
    @Cacheable(value = "prices", key = "#criteria.queryDate + '-' + #criteria.productId + '-' + #criteria.brandId")
    public Optional<Price> getPriceByCriteria(PriceQuery criteria) {
        checkArguments(criteria);
        Date queryDate = criteria.getQueryDate();

        if (queryDate == null) {
            queryDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        }

        return priceRepository.findPriceByCriteria(
                queryDate,
                criteria.getProductId(),
                criteria.getBrandId());
    }
    /**
     * Check if the given arguments are valid
     *
     * @param criteria the criteria containing query date, product id, and brand id.
     * @throws IllegalArgumentException if product id or brand id is null.
     *
     * @since 1.0.0
     * @author Willow Maui Garcia
     */
    private void checkArguments(PriceQuery criteria) {
        if(Objects.isNull(criteria.getProductId()))
            throw new IllegalArgumentException(PRODUCT_ID_VACIO);
        if(Objects.isNull(criteria.getBrandId()))
            throw new IllegalArgumentException(BRAND_ID_VACIO);
    }
}
