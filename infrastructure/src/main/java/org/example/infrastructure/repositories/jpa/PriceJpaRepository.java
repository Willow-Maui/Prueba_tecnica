package org.example.infrastructure.repositories.jpa;


import org.example.infrastructure.entities.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface PriceJpaRepository extends JpaRepository<PriceEntity, Long> {

    @Query("SELECT p " +
            "FROM PriceEntity p " +
            "WHERE p.startDate <= :fechaConsulta AND p.endDate >= :fechaConsulta AND p.productId = :productId AND p.brandId = :brandId " +
            "ORDER BY p.priority DESC, p.startDate DESC LIMIT 1")
    PriceEntity findPriceEntityByCriteria(
            @Param("fechaConsulta") Date fechaConsulta,
            @Param("productId") Long productId,
            @Param("brandId") Long brandId
    );
}
