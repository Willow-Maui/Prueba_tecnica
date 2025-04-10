package org.example.domain.models;

import lombok.Data;
import org.example.domain.models.enums.Currency;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Price {
    private Long brandId;

    private Date startDate;

    private Date endDate;

    private Long priceList;

    private Long productId;

    private Integer priority;

    private BigDecimal price; //NOSONAR Esto se omite al ser una exigencia de la prueba.

    private Currency curr;
}
