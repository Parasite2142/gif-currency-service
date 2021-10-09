package ru.alpha.example.gifcurrencygradle.entites.enums;

public enum WealthStatus {
    RICH, BROKE;

    public static WealthStatus getInstance(boolean isRich) {
        return isRich ? RICH : BROKE;
    }
}
