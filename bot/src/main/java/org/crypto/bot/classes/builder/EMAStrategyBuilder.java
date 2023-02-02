package org.crypto.bot.classes.builder;

import org.crypto.bot.classes.strategies.EMAStrategy;
import org.crypto.bot.enums.Period;

public class EMAStrategyBuilder implements MovingAvgBuilder {
    private EMAStrategy strategy;

    public void reset() {
        this.strategy = new EMAStrategy();
    }

    public void setPeriod(Period period) {
        this.strategy.setPeriod(period);
    }

    public void setNbOfPeriods(int nbOfPeriods) {
        this.strategy.setNbOfPeriods(nbOfPeriods);
    }

    public EMAStrategy getResult() {
        return this.strategy;
    }
}
