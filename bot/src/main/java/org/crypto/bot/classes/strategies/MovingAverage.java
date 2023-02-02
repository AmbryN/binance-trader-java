package org.crypto.bot.classes.strategies;

import org.crypto.bot.classes.selectors.IntSelector;
import org.crypto.bot.classes.selectors.PeriodListSelector;
import org.crypto.bot.enums.Period;
import org.crypto.bot.enums.StrategyResult;
import org.crypto.bot.enums.Symbol;
import org.crypto.bot.interfaces.Exchange;
import org.crypto.bot.interfaces.Strategy;

public abstract class MovingAverage implements Strategy {
    protected Period period;
    protected int nbOfPeriods;
    protected int nbOfRecordsToFetch;
    protected double movingAvg;

//    this.period = new PeriodListSelector().startSelector();
//        this.nbOfPeriods = new IntSelector().startSelector("Moving Average");
//        this.nbOfRecordsToFetch = nbOfPeriods;
    protected MovingAverage() {}

    public void setPeriod(Period period) {
        this.period = period;
    }
    public Period getPeriod() {
        return this.period;
    }

    public void setNbOfPeriods(int nbOfPeriods) {
        this.nbOfPeriods = nbOfPeriods;
        this.nbOfRecordsToFetch = nbOfPeriods;
    }

    @Override
    public StrategyResult execute(double tickerPrice, double[] closePrices) {
        calculateMovingAvg(closePrices);

        StrategyResult result = StrategyResult.HOLD;
        if (tickerPrice > movingAvg) {
            result = StrategyResult.BUY;
        } else if (tickerPrice < movingAvg) {
            result = StrategyResult.SELL;
        }
        return result;
    }

    public String getCurrentStatus() {
        return "\nMAvg " + movingAvg;
    }

    protected abstract void calculateMovingAvg(double[] closePrices);

    @Override
    public String describe() {
        return this +
                "\n-> Time Period: " + this.period +
                "\n-> Number of Periods: " + this.nbOfPeriods;
    }

    @Override
    public int getAmountOfRecordsToFetch() {
        return this.nbOfRecordsToFetch;
    }
}
