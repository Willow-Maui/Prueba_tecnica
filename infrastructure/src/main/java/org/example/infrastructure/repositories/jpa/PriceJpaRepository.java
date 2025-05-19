package org.example.infrastructure.repositories.jpa;

import org.example.infrastructure.entities.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
/**
 * Repository interface for performing operations on PriceEntity.
 *
 * This repository provides methods to perform database operations
 * related to PriceEntity, such as finding a price entity by query parameters.
 *
 * @since 1.0.0
 * @author Willow Maui Garcia
 */
@Repository
public interface PriceJpaRepository extends JpaRepository<PriceEntity, Long>  {


    /**
     * Finds a price entity by query parameters.
     *
     * @param queryDate query date
     * @param productId product id
     * @param brandId brand id
     * @return the price entity that matches the given criteria
     *
     * @since 1.0.0
     * @author Willow Maui Garcia
     */
    @Query("SELECT p " +
            "FROM PriceEntity p " +
            "WHERE p.startDate <= :queryDate AND p.endDate >= :queryDate AND p.productId = :productId AND p.brandId = :brandId " +
            "ORDER BY p.priority DESC, p.startDate DESC LIMIT 1")
    PriceEntity findPriceEntityByQuery(
            @Param("queryDate") Date queryDate,
            @Param("productId") Long productId,
            @Param("brandId") Long brandId
    );

    /**
     * Finds a price entity by query parameters, using an alternative method.
     *
     * @param queryDate query date
     * @param productId product id
     * @param brandId brand id
     * @return the price entity that matches the given criteria
     *
     * @since 1.0.0
     * @author Willow Maui Garcia
     */
    PriceEntity findPriceEntityAlternative(Date queryDate, Long productId,Long brandId);
}
