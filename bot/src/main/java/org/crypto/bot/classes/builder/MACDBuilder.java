package org.crypto.bot.classes.builder;

public interface MACDBuilder extends StrategyBuilder {
    void setShortNbOfPeriods(int shortNbOfPeriods);
    void setLongNbOfPeriods(int longNbOfPeriods);
    void setSignalNbOfPeriods(int signalNbOfPeriods);
}
