package org.example.infrastructure.repositories;

import lombok.RequiredArgsConstructor;
import org.example.application.adapters.in.PriceRepository;
import org.example.domain.models.Price;
import org.example.infrastructure.entities.PriceEntity;
import org.example.infrastructure.mappers.PriceMapper;
import org.example.infrastructure.repositories.jpa.PriceJpaRepository;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Implementation of the PriceRepository interface.
 *
 * <p>This repository implementation provides methods to interact with the Price
 * object in the application layer. It utilizes the PriceJpaRepository for
 * database operations and the PriceMapper for entity-model mapping.</p>
 *
 * <p>The repository supports switching query logic based on active Spring profiles.
 * If the active profiles contain "criteria", a custom query logic is used; otherwise,
 * a predefined query is executed.</p>
 *
 * @since 1.0.0
 * @author Willow Maui Garcia
 */
@Repository
@RequiredArgsConstructor
public class PriceRepositoryImpl implements PriceRepository {

    public static final String CRITERIA = "criteria";
    public static final String QUERY = "query";
    public static final String SPRING_PROFILES_ACTIVE = "spring.profiles.active";
    private final PriceJpaRepository priceJpaRepository;
    private final PriceMapper priceMapper ;
    private final Environment environment;

    /**
     * Finds a price based on the given criteria.
     *
     * @param queryDate the date to search for a price entity
     * @param productId the id of the product
     * @param brandId the id of the brand
     * @return the price found
     *
     * @since 1.0.0
     * @author Willow Maui Garcia
     */
    @Override
    public Optional<Price> findPriceByCriteria(Date queryDate, Long productId, Long brandId) {
        PriceEntity priceEntity=null;
        String[] activeProfiles = environment.getActiveProfiles();

        List<String> profiles = Arrays.asList(activeProfiles);

        if (profiles.contains(CRITERIA)) {
            priceEntity = priceJpaRepository.findPriceEntityAlternative(queryDate, productId, brandId);
        } else {
            priceEntity = priceJpaRepository.findPriceEntityByQuery(queryDate, productId, brandId);
        }

        if (Objects.isNull(priceEntity)) {
            return Optional.empty();
        }
        return Optional.of(priceMapper.priceEntityToPrice(priceEntity));
    }
}