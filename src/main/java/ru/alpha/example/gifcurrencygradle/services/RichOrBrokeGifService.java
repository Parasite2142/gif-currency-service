package ru.alpha.example.gifcurrencygradle.services;

import java.util.Collection;
import java.util.List;

public interface RichOrBrokeGifService {

    byte[] getGifDependingOnCurrencyChangeToBaseCurrency(String currency) throws IllegalArgumentException;

    Collection<String> getSupportedCurrencies();
}
