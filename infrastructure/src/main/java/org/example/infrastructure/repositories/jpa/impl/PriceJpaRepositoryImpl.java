package org.example.infrastructure.repositories.jpa.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.infrastructure.entities.PriceEntity;

import java.util.Date;

public class PriceJpaRepositoryImpl {

    @PersistenceContext
    private EntityManager entityManager;
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
