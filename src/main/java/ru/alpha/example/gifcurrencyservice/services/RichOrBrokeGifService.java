package ru.alpha.example.gifcurrencyservice.services;

import java.util.Collection;

public interface RichOrBrokeGifService {

    byte[] getGifDependingOnCurrencyChangeToBaseCurrency(String currency) throws IllegalArgumentException;

    Collection<String> getSupportedCurrencies();
}
