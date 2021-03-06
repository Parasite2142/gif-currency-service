package ru.alpha.example.gifcurrencyservice.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.alpha.example.gifcurrencyservice.entites.CurrencyInfoMap;

import java.time.LocalDate;

@FeignClient(url = "${api.currency.url}", primary = false, name = "currencyClient")
public interface FeignCurrencyClient {

    @GetMapping(path = "/api/historical/{date}.json", produces = MediaType.APPLICATION_JSON_VALUE)
    CurrencyInfoMap getHistoricalCurrencyInfoForDate(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date);

    @GetMapping(path = "/api/latest.json", produces = MediaType.APPLICATION_JSON_VALUE)
    CurrencyInfoMap getLatestCurrencyInfo();

}
