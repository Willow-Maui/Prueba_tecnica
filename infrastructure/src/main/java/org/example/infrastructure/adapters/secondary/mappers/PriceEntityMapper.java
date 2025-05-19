package org.example.infrastructure.adapters.secondary.mappers;

import org.example.domain.models.Price;
import org.example.domain.models.enums.Currency;
import org.example.infrastructure.adapters.secondary.entities.PriceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper between the domain model and the DTOs.
 *
 * @since 1.0.0
 * @author Willow Maui García
 */
@Mapper(componentModel = "spring")
public interface PriceEntityMapper {

    @Mapping(source = "curr", target = "curr", qualifiedByName = "mapCurrency")
    Price priceEntityToPrice(PriceEntity priceEntity);

    @Named("mapCurrency")
    default Currency mapCurrency(String curr) {
        if (curr == null) {
            return null;
        }
        return Currency.fromValue(curr).orElse(null);
    }

}