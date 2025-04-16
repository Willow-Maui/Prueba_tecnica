package org.example.application.services;

import lombok.RequiredArgsConstructor;
import org.example.domain.services.PriceService;
import org.example.domain.models.Price;
import org.example.domain.models.querys.PriceQuery;
import org.example.application.adapters.in.PriceRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService{

    public static final String PRODUCT_ID_VACIO = "Product id vacío.";
    private final PriceRepository priceRepository;

    @Override
    @Transactional
    @Cacheable(value = "prices", key = "#criteria.fechaConsulta + '-' + #criteria.productId + '-' + #criteria.brandId")
    public Price getPriceByCriteria(PriceQuery criteria) {
        checkArguments(criteria);
        Date fechaConsulta = criteria.getFechaConsulta();

        if (fechaConsulta == null) {
            fechaConsulta = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        }

        return priceRepository.findPriceByCriteria(
                fechaConsulta,
                criteria.getProductId(),
                criteria.getBrandId());
    }

    private void checkArguments(PriceQuery criteria) {
        if(Objects.isNull(criteria.getProductId()))
            throw new IllegalArgumentException(PRODUCT_ID_VACIO);
        if(Objects.isNull(criteria.getBrandId()))
            throw new IllegalArgumentException("Brand id vacío.");
    }
}
