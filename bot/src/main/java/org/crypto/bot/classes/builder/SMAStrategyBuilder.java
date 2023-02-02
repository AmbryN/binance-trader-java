package org.crypto.bot.classes.builder;

import org.crypto.bot.classes.strategies.SMAStrategy;
import org.crypto.bot.enums.Period;

public class SMAStrategyBuilder implements MovingAvgBuilder {
    private SMAStrategy strategy;

    public void reset() {
        this.strategy = new SMAStrategy();
    }

    public void setPeriod(Period period) {
        this.strategy.setPeriod(period);
    }

    public void setNbOfPeriods(int nbOfPeriods) {
        this.strategy.setNbOfPeriods(nbOfPeriods);
    }

    public SMAStrategy getResult() {
        return this.strategy;
    }
}
