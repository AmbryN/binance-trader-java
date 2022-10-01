package com.binance.trader.enums;

public enum Period {
    OneSecond("1s"),
    OneMinute("1m"),
    ThreeMinutes("3m"),
    FiveMinutes("5m"),
    FifteenMinutes("15m"),
    ThirtyMinutes("30m"),
    OneHour("1h"),
    TwoHours("2h"),
    FourHours("4h"),
    SixHours("6h"),
    EightHours("8h"),
    TwelveHours("12h"),
    OneDay("1d"),
    ThreeDays("3d"),
    OneWeek("1w"),
    OneMonth("1M");

    private final String periodAsStr;
    Period(String periodAsStr) {
        this.periodAsStr = periodAsStr;
    }

    public String asString() {
        return this.periodAsStr;
    }
}
