package org.example.infrastructure.repositories;

import lombok.RequiredArgsConstructor;
import org.example.application.adapters.in.PriceRepository;
import org.example.domain.models.Price;
import org.example.infrastructure.entities.PriceEntity;
import org.example.infrastructure.mappers.PriceMapper;
import org.example.infrastructure.repositories.jpa.PriceJpaRepository;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class PriceRepositoryImpl implements PriceRepository {

    public static final String CRITERIA = "criteria";
    public static final String QUERY = "query";
    public static final String SPRING_PROFILES_ACTIVE = "spring.profiles.active";
    private final PriceJpaRepository priceJpaRepository;
    private final PriceMapper priceMapper ;
    private final Environment environment;

    @Override
    public Price findPriceByCriteria(Date fechaConsulta, Long productId, Long brandId) {
        PriceEntity priceEntity=null;
        String[] activeProfiles = environment.getActiveProfiles();

        List<String> profiles = Arrays.asList(activeProfiles);

        if (profiles.contains(CRITERIA)) {
            priceEntity = priceJpaRepository.findPriceEntityAlternative(fechaConsulta, productId, brandId);
        } else {
            priceEntity = priceJpaRepository.findPriceEntityByQuery(fechaConsulta, productId, brandId);
        }

        if (Objects.isNull(priceEntity)) {
            return null;
        }
        return priceMapper.priceEntityToPrice(priceEntity);
    }
}