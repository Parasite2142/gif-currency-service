package ru.alpha.example.gifcurrencygradle.entites;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class CurrencyInfoMap {
    @JsonProperty(value = "base")
    private String baseCurrency;

    @JsonProperty(value = "rates")
    private Map<String, BigDecimal> rates;


    public BigDecimal getValueForCurrencyName(String name) {
        return rates.get(name.toUpperCase());
    }

    public boolean contains(String currency) {
        return rates.containsKey(currency.toUpperCase());
    }
}
