package org.example.domain.models.enums;

public enum Currency {
    EUR("EUR");

    private final String valor;

    Currency(String valor) {
        this.valor = valor;
    }

    public static Currency fromValor(String valor) {
        for (Currency currency : Currency.values()) {
            if (currency.valor.equalsIgnoreCase(valor)) {
                return currency;
            }
        }
        return null;
    }

    public String getValor() {
        return valor;
    }

}
