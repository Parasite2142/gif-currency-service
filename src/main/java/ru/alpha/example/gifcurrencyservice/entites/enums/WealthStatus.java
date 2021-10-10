package ru.alpha.example.gifcurrencyservice.entites.enums;

public enum WealthStatus {
    RICH, BROKE;

    public static WealthStatus getInstance(boolean isRich) {
        return isRich ? RICH : BROKE;
    }
}
