package ru.alpha.example.gifcurrencygradle.services.impls;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import ru.alpha.example.gifcurrencygradle.entites.CurrencyInfoMap;
import ru.alpha.example.gifcurrencygradle.entites.GiphyObject;
import ru.alpha.example.gifcurrencygradle.entites.enums.WealthStatus;
import ru.alpha.example.gifcurrencygradle.feignclients.FeignCurrencyClient;
import ru.alpha.example.gifcurrencygradle.feignclients.FeignGiphyClient;
import ru.alpha.example.gifcurrencygradle.feignclients.FeignGiphyMediaClient;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.times;


@ExtendWith(MockitoExtension.class)
class RichOrBrokeServiceImplTest {

    private final static CurrencyInfoMap lastDayInfo = new CurrencyInfoMap();
    private final static CurrencyInfoMap currentDayInfo = new CurrencyInfoMap();

    @Mock
    private FeignGiphyClient feignGiphyClient;
    @Mock
    private FeignGiphyMediaClient feignGiphyMediaClient;
    @Mock
    private FeignCurrencyClient feignCurrencyClient;

    @InjectMocks
    private RichOrBrokeServiceImpl richOrBrokeGifService;

    @BeforeAll
    static void init() {
        lastDayInfo.setBaseCurrency("USD");
        Map<String, BigDecimal> lastDayRates = new HashMap<>();
        lastDayRates.put("AED", BigDecimal.valueOf(3.67295));
        lastDayRates.put("USD", BigDecimal.ONE);
        lastDayRates.put("RUB", BigDecimal.valueOf(71.8131));
        lastDayRates.put("EUR", BigDecimal.valueOf(0.864379));
        lastDayInfo.setRates(lastDayRates);

        currentDayInfo.setBaseCurrency("USD");
        Map<String, BigDecimal> currentRates = new HashMap<>();
        currentRates.put("AED", BigDecimal.valueOf(3.77295));
        currentRates.put("USD", BigDecimal.ONE);
        currentRates.put("RUB", BigDecimal.valueOf(72.8131));
        currentRates.put("EUR", BigDecimal.valueOf(0.854379));
        currentDayInfo.setRates(currentRates);
    }

    @BeforeEach
    void setUp() {
        given(feignCurrencyClient.getHistoricalCurrencyInfoForDate(LocalDate.now().minusDays(1)))
                .willReturn(lastDayInfo);
        ReflectionTestUtils.setField(richOrBrokeGifService, "baseCurrency", "RUB");
        richOrBrokeGifService.init();
    }

    @Test
    void getGifForValueUpOnCurrency() {
        GiphyObject giphyObject = GiphyObject.create("123456");
        byte[] gif = new byte[100];

        given(feignCurrencyClient.getLatestCurrencyInfo()).willReturn(currentDayInfo);
        given(feignGiphyClient.getRandomGiphyObjectByWealthTag(WealthStatus.RICH)).willReturn(giphyObject);
        given(feignGiphyMediaClient.getGifById(giphyObject.getId())).willReturn(gif);

        byte[] result = richOrBrokeGifService.getGifDependingOnCurrencyChangeToBaseCurrency("EUR");

        then(feignCurrencyClient)
                .should(times(1))
                .getLatestCurrencyInfo();
        then(feignGiphyClient)
                .should(times(1))
                .getRandomGiphyObjectByWealthTag(WealthStatus.RICH);
        then(feignGiphyMediaClient)
                .should(times(1))
                .getGifById(giphyObject.getId());

        assertThat(result).isEqualTo(gif);
    }

    @Test
    void getGifForValueDownOnCurrency() {
        GiphyObject giphyObject = GiphyObject.create("123456");
        byte[] gif = new byte[100];

        given(feignCurrencyClient.getLatestCurrencyInfo()).willReturn(currentDayInfo);
        given(feignGiphyClient.getRandomGiphyObjectByWealthTag(WealthStatus.BROKE)).willReturn(giphyObject);
        given(feignGiphyMediaClient.getGifById(giphyObject.getId())).willReturn(gif);

        byte[] result = richOrBrokeGifService.getGifDependingOnCurrencyChangeToBaseCurrency("AED");

        then(feignCurrencyClient)
                .should(times(1))
                .getLatestCurrencyInfo();
        then(feignGiphyClient)
                .should(times(1))
                .getRandomGiphyObjectByWealthTag(WealthStatus.BROKE);
        then(feignGiphyMediaClient)
                .should(times(1))
                .getGifById(giphyObject.getId());

        assertThat(result).isEqualTo(gif);
    }

    @Test
    void getSupportedCurrencies() {
        assertThat(richOrBrokeGifService.getSupportedCurrencies()).isEqualTo(lastDayInfo.getCurrencyCodes());
    }

    @Test
    void throwsErrorUnknownCurrency() {
        String wrongCurrency = "wrong";

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> richOrBrokeGifService.getGifDependingOnCurrencyChangeToBaseCurrency(wrongCurrency));

        assertThat(exception.getMessage()).isEqualTo("Unknown currency code: " + wrongCurrency);
    }

    @Test
    void throwsErrorBaseCurrency() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> richOrBrokeGifService.getGifDependingOnCurrencyChangeToBaseCurrency("RUB"));

        assertThat(exception.getMessage()).isEqualTo("Can't compare value change of same currency");
    }
}