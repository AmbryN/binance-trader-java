package com.binance.trader.classes.strategies;

import com.binance.trader.classes.selectors.IntSelector;
import com.binance.trader.classes.selectors.PeriodListSelector;
import com.binance.trader.enums.Period;
import com.binance.trader.enums.StrategyResult;
import com.binance.trader.enums.Symbol;
import com.binance.trader.interfaces.Exchange;
import com.binance.trader.interfaces.Strategy;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class MovingAverage implements Strategy {
    protected Exchange exchange;
    protected Period period;
    protected int nbOfPeriods;

    protected MovingAverage() {}

    public void init(Exchange exchange) {
        this.exchange = exchange;
        this.period = new PeriodListSelector().startSelector();
        this.nbOfPeriods = new IntSelector().startSelector("Moving Average");
    }

    protected void setPeriod(Period period) {
        this.period = period;
    }

    @Override
    public StrategyResult execute(Symbol symbol, HashMap<String, Double> balances, double tickerPrice) {
        double movingAvg = this.calculateMovingAvg(symbol);

        System.out.println("Base balance: " + balances.get("base") +
                "\nQuote balance: " + balances.get("quote") +
                "\nTicker " + tickerPrice +
                "\nExpMAvg " + movingAvg);

        StrategyResult result = StrategyResult.NONE;
        if (tickerPrice > movingAvg && balances.get("quote") > symbol.MIN_QUOTE_TRANSACTION) {
            result = StrategyResult.BUY;
        } else if (tickerPrice < movingAvg && balances.get("base") > symbol.MIN_BASE_TRANSACTION) {
            result = StrategyResult.SELL;
        }
        return result;
    }

    protected abstract double calculateMovingAvg(Symbol symbol);

    protected ArrayList<Double> getClosePrices(Symbol symbol, String periodAsStr, int nbOfRecordsToFetch) {
        return exchange.getClosePrices(symbol, periodAsStr, nbOfRecordsToFetch);
    }

    public abstract String toString();

    @Override
    public String describe() {
        return "Time Period: " + this.period +
                "\nNumber of Periods: " + this.nbOfPeriods;
    }
}
