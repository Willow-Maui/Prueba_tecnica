package org.example.domain.models.querys;

import lombok.Data;

import java.util.Date;

@Data
public class PriceQuery {
    private Date queryDate;

    private Long productId;

    private Long brandId;
}
