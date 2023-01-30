package org.crypto.bot.classes.strategies;

import org.crypto.bot.classes.selectors.IntSelector;
import org.crypto.bot.classes.selectors.PeriodListSelector;
import org.crypto.bot.enums.Period;
import org.crypto.bot.enums.StrategyResult;
import org.crypto.bot.enums.Symbol;
import org.crypto.bot.interfaces.Exchange;
import org.crypto.bot.interfaces.Strategy;

import java.util.HashMap;

public abstract class MovingAverage implements Strategy {
    protected Exchange exchange;
    protected Period period;
    protected int nbOfPeriods;
    protected double movingAvg;

    protected MovingAverage() {}

    public void init(Exchange exchange) {
        this.exchange = exchange;
        this.period = new PeriodListSelector().startSelector();
        this.nbOfPeriods = new IntSelector().startSelector("Moving Average");
    }

    protected void setPeriod(Period period) {
        this.period = period;
    }
    public Period getPeriod() {
        return this.period;
    }
    protected void setNbOfPeriods(int nbOfPeriods) { this.nbOfPeriods = nbOfPeriods; }

    @Override
    public StrategyResult execute(Symbol symbol, double tickerPrice) {
        calculateMovingAvg(symbol);

        StrategyResult result = StrategyResult.HOLD;
        if (tickerPrice > movingAvg) {
            result = StrategyResult.BUY;
        } else if (tickerPrice < movingAvg) {
            result = StrategyResult.SELL;
        }
        return result;
    }

    @Override
    public void printCurrentStatus(HashMap<String, Double> balances, double tickerPrice) {
        System.out.println("Base balance: " + balances.get("base") +
                "\nQuote balance: " + balances.get("quote") +
                "\nTicker " + tickerPrice +
                "\nMAvg " + movingAvg);
    }

    protected abstract void calculateMovingAvg(Symbol symbol);

    protected Double[] getClosePrices(Symbol symbol, String periodAsStr, int nbOfRecordsToFetch) {
        return exchange.getClosePrices(symbol, periodAsStr, nbOfRecordsToFetch);
    }

    public abstract String toString();

    @Override
    public String describe() {
        return "Time Period: " + this.period +
                "\nNumber of Periods: " + this.nbOfPeriods;
    }
}
