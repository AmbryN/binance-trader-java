package org.crypto.bot.classes.builder;

import org.crypto.bot.classes.strategies.MACDr2Strategy;
import org.crypto.bot.enums.Period;

public class MACDr2StrategyBuilder implements MACDBuilder {
    private MACDr2Strategy strategy;

    public void reset() {
        this.strategy = new MACDr2Strategy();
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

    public MACDr2Strategy getResult() {
        return this.strategy;
    }
}
