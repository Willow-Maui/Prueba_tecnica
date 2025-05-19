package org.example.infrastructure.adapters.secondary.repositories.jpa.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.infrastructure.adapters.secondary.entities.PriceEntity;

import java.util.Date;

/**
 * Implementation of the PriceJpaRepository for custom queries.
 *
 * <p>This class should be used when custom query logic is needed beyond the
 * capabilities provided by the Spring Data JPA repository methods.</p>
 *
 * @since 1.0.0
 * @author Willow Maui García
 */
public class PriceJpaRepositoryImpl {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Retrieves a price entity that matches the given criteria. The criteria is a date which
     * is used to search for a price entity that has a start date less than or equal to the
     * given date and an end date greater than or equal to the given date. The query also
     * takes in a product id and a brand id to narrow the search.
     *
     * @param queryDate the date to search for a price entity
     * @param productId the id of the product
     * @param brandId the id of the brand
     * @return the price entity that matches the given criteria
     *
     * @since 2.0.0
     * @author Willow Maui García
     */
    public PriceEntity findPriceEntityAlternative(Date queryDate, Long productId, Long brandId){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PriceEntity> criteriaQuery = criteriaBuilder.createQuery(PriceEntity.class);

        Root<PriceEntity> root = criteriaQuery.from(PriceEntity.class);

        criteriaQuery.where(
                criteriaBuilder.lessThanOrEqualTo(root.get("startDate"), queryDate),
                criteriaBuilder.greaterThanOrEqualTo(root.get("endDate"), queryDate),
                criteriaBuilder.equal(root.get("productId"), productId),
                criteriaBuilder.equal(root.get("brandId"), brandId)
        );

        criteriaQuery.orderBy(
                criteriaBuilder.desc(root.get("priority")),
                criteriaBuilder.desc(root.get("startDate"))
        );

        TypedQuery<PriceEntity> query = entityManager.createQuery(criteriaQuery);
        query.setMaxResults(1);
        return query.getSingleResult();
    }
}
