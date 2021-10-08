package ru.alpha.example.gifcurrencygradle.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.alpha.example.gifcurrencygradle.entites.CurrencyInfoMap;
import ru.alpha.example.gifcurrencygradle.feignclients.CurrencyClient;
import ru.alpha.example.gifcurrencygradle.services.CompareCurrencyService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CompareCurrencyServiceImpl implements CompareCurrencyService {

    @Value("${baseCurrency:RUB}")
    private final String baseCurrency;

    private final CurrencyClient currencyClient;

    @Override
    public boolean currencyUpRelativeToBase(String currency) {
        CurrencyInfoMap dayBeforeData =
                currencyClient.getHistoricalCurrencyInfoForDate(LocalDate.now().minusDays(1));

        CurrencyInfoMap currentData =
                currencyClient.getLatestCurrencyInfo();

        BigDecimal baseChange = checkPercentageChange(
                dayBeforeData.getValueForCurrencyName(baseCurrency),
                currentData.getValueForCurrencyName(baseCurrency)
        );

        BigDecimal specifiedChange = checkPercentageChange(
                dayBeforeData.getValueForCurrencyName(currency),
                currentData.getValueForCurrencyName(currency)
        );

        return specifiedChange.compareTo(baseChange) > 0;
    }

    private static BigDecimal checkPercentageChange(BigDecimal oldValue, BigDecimal newValue) {
        if (oldValue.compareTo(newValue) < 0) {
            return newValue
                    .subtract(oldValue)
                    .divide(oldValue, 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100L))
                    .negate();
        } else {
            return oldValue
                    .subtract(newValue)
                    .divide(oldValue, 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100L));
        }
    }
}
