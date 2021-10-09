package ru.alpha.example.gifcurrencygradle.services.impls;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.alpha.example.gifcurrencygradle.entites.CurrencyInfoMap;
import ru.alpha.example.gifcurrencygradle.entites.GiphyObject;
import ru.alpha.example.gifcurrencygradle.entites.enums.WealthStatus;
import ru.alpha.example.gifcurrencygradle.feignclients.FeignCurrencyClient;
import ru.alpha.example.gifcurrencygradle.feignclients.FeignGiphyClient;
import ru.alpha.example.gifcurrencygradle.feignclients.FeignGiphyMediaClient;
import ru.alpha.example.gifcurrencygradle.services.RichOrBrokeGifService;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicReference;

@Log4j2
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RichOrBrokeServiceImpl implements RichOrBrokeGifService {

    @Value("${baseCurrency:RUB}")
    private final String baseCurrency;

    private final FeignCurrencyClient feignCurrencyClient;
    private final FeignGiphyClient feignGiphyClient;
    private final FeignGiphyMediaClient feignGiphyMediaClient;

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
    public byte[] getGifDependingOnCurrencyChangeToBaseCurrency(String currency) {
        Assert.isTrue(!currency.equalsIgnoreCase(baseCurrency), "Can't compare value change of same currency");
        // If value of passed currency went up relative to base currency get Rich gif object else Broke
        GiphyObject giphyObject = feignGiphyClient.getRandomGiphyObjectByWealthTag(checkWealthChangeForCurrency(currency));

        return feignGiphyMediaClient.getGifById(giphyObject.getId());
    }

    @Override
    public Collection<String> getSupportedCurrencies() {
        return dayBeforeDataReference.get().getCurrencyCodes();
    }

    private WealthStatus checkWealthChangeForCurrency(String currency) {
        CurrencyInfoMap dayBeforeData = dayBeforeDataReference.get();

        Assert.isTrue(dayBeforeData.contains(currency), "Unknown currency code: " + currency);

        CurrencyInfoMap currentData = feignCurrencyClient.getLatestCurrencyInfo();
        // calculating percentageChange of base currency
        BigDecimal baseCurrencyChange = checkPercentageChange(
                dayBeforeData.getValueForCurrencyName(baseCurrency),
                currentData.getValueForCurrencyName(baseCurrency)
        );
        // calculating percentageChange of passed currency
        BigDecimal currencyChange = checkPercentageChange(
                dayBeforeData.getValueForCurrencyName(currency),
                currentData.getValueForCurrencyName(currency)
        );

        log.info("Base currency: " + baseCurrency + "\n" +
                "       Old base currency value: " + dayBeforeData.getValueForCurrencyName(baseCurrency) + "\n" +
                "       Current base currency value: " + currentData.getValueForCurrencyName(baseCurrency) + "\n" +
                "       Base currencyChange relative to USD: " + baseCurrencyChange);

        log.info("Passed currency: " + currency + "\n" +
                "       Old passed currency value: " + dayBeforeData.getValueForCurrencyName(currency) + "\n" +
                "       Current passed currency value: " + currentData.getValueForCurrencyName(currency) + "\n" +
                "       Passed currencyChange relative to USD: " + currencyChange);

        return WealthStatus.getInstance(currencyChange.compareTo(baseCurrencyChange) > 0);
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
