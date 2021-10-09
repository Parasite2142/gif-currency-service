package ru.alpha.example.gifcurrencygradle.entites.enums;

public enum WealthStatus {
    RICH, BROkE;

    public static WealthStatus getInstance(boolean isRich) {
        return isRich ? RICH : BROkE;
    }
}
