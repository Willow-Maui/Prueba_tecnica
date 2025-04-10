package org.example.infrastructure.repositories;

import lombok.RequiredArgsConstructor;
import org.example.domain.models.Price;
import org.example.application.adapters.in.PriceRepository;
import org.example.infrastructure.entities.PriceEntity;
import org.example.infrastructure.mappers.PriceMapper;
import org.example.infrastructure.repositories.jpa.PriceJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class PriceRepositoryImpl implements PriceRepository {

    private final PriceJpaRepository priceJpaRepository;
    private final PriceMapper priceMapper ;


    @Override
    public Price findPriceByCriteria(Date fechaConsulta, Long productId, Long brandId) {
        PriceEntity priceEntity = priceJpaRepository.findPriceEntityByCriteria(fechaConsulta, productId, brandId);
        if (Objects.isNull(priceEntity)) {
            return null;
        }
        return priceMapper.priceEntityToPrice(priceEntity);
    }

    }