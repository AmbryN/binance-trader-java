package org.crypto.bot.enums;

/**
 * Represents the period of the candle / kline graph used
 * for computing the technical indicators.
 */
public enum Period {
    OneSecond("1s", 1000L),
    OneMinute("1m", 60000L),
    ThreeMinutes("3m", 180000L),
    FiveMinutes("5m", 300000L),
    FifteenMinutes("15m", 900000L),
    ThirtyMinutes("30m", 1800000L),
    OneHour("1h", 3600000L),
    TwoHours("2h", 7200000L),
    FourHours("4h", 14400000L),
    SixHours("6h", 21600000L),
    EightHours("8h", 28800000L),
    TwelveHours("12h", 43200000L),
    OneDay("1d", 86400000L),
    ThreeDays("3d", 259200000L),
    OneWeek("1w", 604800000L),
    OneMonth("1M", 2419200000L);

    private final String periodAsStr;
    private final Long periodAsMs;

    Period(String periodAsStr, Long periodAsMs) {
        this.periodAsStr = periodAsStr;
        this.periodAsMs = periodAsMs;
    }

    @Override
    public String toString() {
        return this.periodAsStr;
    }

    /**
     * Acts as a getter for the millisecond value of the Period.
     * @return the period as milliseconds
     */
    public Long toMillis() {
        return this.periodAsMs;
    }
}
