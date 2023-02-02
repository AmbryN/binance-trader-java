package org.crypto.bot.classes.builder;

import org.crypto.bot.classes.strategies.MACDStrategy;
import org.crypto.bot.enums.Period;

public class MACDStrategyBuilder implements MACDBuilder {
    private MACDStrategy strategy;

    public void reset() {
        this.strategy = new MACDStrategy();
    }

    public void setPeriod(Period period) {
        this.strategy.setPeriod(period);
    }

    public void setShortNbOfPeriods(int shortNbOfPeriods) {
        this.strategy.setShortNbOfPeriods(shortNbOfPeriods);
    }

    public void setLongNbOfPeriods(int longNbOfPeriods) {
        this.strategy.setLongNbOfPeriods(longNbOfPeriods);
    }

    public void setSignalNbOfPeriods(int signalNbOfPeriods) {
        this.strategy.setSignalNbOfPeriods(signalNbOfPeriods);
    }

    public MACDStrategy getResult() {
        return this.strategy;
    }
}
