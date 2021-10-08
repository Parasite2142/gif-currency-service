package ru.alpha.example.gifcurrencygradle.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alpha.example.gifcurrencygradle.entites.CurrencyInfoMap;
import ru.alpha.example.gifcurrencygradle.feignclients.CurrencyClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final CurrencyClient currencyClient;

    @GetMapping("/")
    CurrencyInfoMap test() {
        return currencyClient.getHistoricalCurrencyInfoForDate(LocalDate.now().minusDays(1));
    }

    public static void main(String[] args) {
        System.out.println(LocalDate.now());
        System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now()));
    }
}
