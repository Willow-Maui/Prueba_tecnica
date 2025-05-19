package org.example.domain.models.enums;

import lombok.Getter;

@Getter
public enum Currency {
    EUR("EUR");

    private final String value;

    Currency(String value) {
        this.value = value;
    }

    public static Currency fromValue(String value) {
        for (Currency currency : Currency.values()) {
            if (currency.value.equalsIgnoreCase(value)) {
                return currency;
            }
        }
        return null;
    }
}
