package ru.alpha.example.gifcurrencygradle.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.alpha.example.gifcurrencygradle.entites.CurrencyInfoMap;
import ru.alpha.example.gifcurrencygradle.feignclients.FeignCurrencyClient;
import ru.alpha.example.gifcurrencygradle.services.CompareCurrencyService;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CompareCurrencyServiceImpl implements CompareCurrencyService {

    @Value("${baseCurrency:RUB}")
    private final String baseCurrency;

    private final FeignCurrencyClient feignCurrencyClient;

    private final AtomicReference<CurrencyInfoMap> dayBeforeDataReference = new AtomicReference<>();

    @PostConstruct
    void init() {
        update();

        Assert.isTrue(dayBeforeDataReference.get().contains(baseCurrency), "Unknown currency code: " + baseCurrency);
    }

    // to save limited usage of api
    @Scheduled(cron = "0 1 0 * * ?")
    private void update() {
        dayBeforeDataReference.set(feignCurrencyClient.getHistoricalCurrencyInfoForDate(LocalDate.now().minusDays(1)));
    }

    @Override
    public boolean currencyUpRelativeToBase(String currency) {
//        CurrencyInfoMap dayBeforeData = feignCurrencyClient.getHistoricalCurrencyInfoForDate(LocalDate.now().minusDays(1));
        CurrencyInfoMap dayBeforeData = dayBeforeDataReference.get();

        Assert.isTrue(dayBeforeData.contains(currency), "Unknown currency code: " + currency);

        CurrencyInfoMap currentData = feignCurrencyClient.getLatestCurrencyInfo();

        BigDecimal baseCurrencyChange = checkPercentageChange(
                dayBeforeData.getValueForCurrencyName(baseCurrency),
                currentData.getValueForCurrencyName(baseCurrency)
        );

        BigDecimal specifiedCurrencyChange = checkPercentageChange(
                dayBeforeData.getValueForCurrencyName(currency),
                currentData.getValueForCurrencyName(currency)
        );

        return specifiedCurrencyChange.compareTo(baseCurrencyChange) > 0;
    }

    private static BigDecimal checkPercentageChange(BigDecimal oldValue, BigDecimal newValue) {
        if (oldValue.compareTo(newValue) < 0) {
            return newValue.subtract(oldValue)
                    .divide(oldValue, 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100L))
                    .negate();
        } else {
            return oldValue.subtract(newValue)
                    .divide(oldValue, 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100L));
        }
    }
}
