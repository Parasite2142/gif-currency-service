package ru.alpha.example.gifcurrencygradle.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.alpha.example.gifcurrencygradle.services.RichOrBrokeGifService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/rich-broke")
public class RichOrBrokenController {

    private final RichOrBrokeGifService richOrBrokeGifService;

    @GetMapping(path = "/{currencyCode}", produces = MediaType.IMAGE_GIF_VALUE)
    public byte[] getGifDependingOnCurrencyChange(@PathVariable String currencyCode) {
        try {
            return richOrBrokeGifService.getGifDependingOnCurrencyChangeToBaseCurrency(currencyCode);
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, illegalArgumentException.getMessage());
        }
    }

    @GetMapping(path = "/currencies")
    public Collection<String> getSupportedCurrencies() {
        return richOrBrokeGifService.getSupportedCurrencies();
    }
}
