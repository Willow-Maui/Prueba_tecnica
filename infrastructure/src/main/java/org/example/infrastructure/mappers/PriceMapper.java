package org.example.infrastructure.mappers;

import org.example.infrastructure.dtos.request.PriceRequest;
import org.example.infrastructure.dtos.response.PriceResponse;
import org.example.infrastructure.entities.PriceEntity;
import org.example.domain.models.Price;
import org.example.domain.models.enums.Currency;
import org.example.domain.models.querys.PriceQuery;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PriceMapper {

    @Mapping(source = "curr", target = "curr", qualifiedByName = "mapCurrency")
    Price priceEntityToPrice(PriceEntity priceEntity);

    @Mapping(source = "curr", target = "curr", qualifiedByName = "currencyGetValue")
    PriceResponse priceToPriceResponse(Price price);

    PriceQuery requestToQuery(PriceRequest priceRequest);

    @Named("mapCurrency")
    default Currency mapCurrency(String curr) {
        if (curr == null) {
            return null;
        }
        return Currency.fromValor(curr);
    }

    @Named("currencyGetValue")
    default String currencyGetValue(Currency curr) {
        if (curr == null) {
            return null;
        }
        return curr.getValor();
    }
}