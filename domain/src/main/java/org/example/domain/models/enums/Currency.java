package org.example.domain.models.enums;

import lombok.Getter;
/**
 * Enumerated type with the possible currencies.
 *
 * <p>This enumerated type follows the ISO 4217 standard which defines the
 * three-character codes for the representation of currencies and funds.
 *
 * <p>Below is the list of possible values for this enumerated type:
 * <ul>
 *     <li>{@link #EUR}</li>
 * </ul>
 *
 * @since 1.0.0
 * @author Willow Maui García
 */
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
