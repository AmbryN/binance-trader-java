package org.crypto.bot.classes.builder;

import org.crypto.bot.classes.strategies.MACDr1Strategy;
import org.crypto.bot.enums.Period;

public class MACDr1StrategyBuilder implements MACDBuilder {
    private MACDr1Strategy strategy;

    public void reset() {
        this.strategy = new MACDr1Strategy();
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

    public void setMinSpread(double spread) {
        this.strategy.setMinSpread(spread);
    }

    public MACDr1Strategy getResult() {
        return this.strategy;
    }
}
