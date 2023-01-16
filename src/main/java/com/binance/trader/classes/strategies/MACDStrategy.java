package com.binance.trader.classes.strategies;

import com.binance.trader.classes.selectors.IntSelector;
import com.binance.trader.classes.selectors.PeriodListSelector;
import com.binance.trader.enums.*;
import com.binance.trader.enums.Symbol;
import com.binance.trader.interfaces.Exchange;
import com.binance.trader.interfaces.Strategy;
import com.binance.trader.utils.Calculus;

import java.util.ArrayList;
import java.util.HashMap;

import static com.binance.trader.enums.CrossingDirection.*;

public class MACDStrategy implements Strategy {
    private Exchange exchange;
    private Period period;
    private int shortNbOfPeriods;
    private int longNbOfPeriods;
    private int signalNbOfPeriods;
    private Double[] MACDLine;
    private Double[] signalLine;
    protected CrossingDirection crossingDirection;

    public MACDStrategy() {
        this.crossingDirection = NONE;
    }

    protected void setPeriod(Period period) { this.period = period; }

    protected void setShortNbOfPeriods(int shortNbOfPeriods) {
        this.shortNbOfPeriods = shortNbOfPeriods;
    }

    protected void setLongNbOfPeriods(int longNbOfPeriods) {
        this.longNbOfPeriods = longNbOfPeriods;
    }

    protected void setSignalNbOfPeriods(int signalNbOfPeriods) {
        this.signalNbOfPeriods = signalNbOfPeriods;
    }

    protected double getCurrentMACD() {
        return this.MACDLine[this.MACDLine.length -1];
    }

    protected double getCurrentSignal() {
        return this.signalLine[this.signalLine.length -1];
    }

    public void init(Exchange exchange) {
        this.exchange = exchange;
        this.period = new PeriodListSelector().startSelector();
        IntSelector selector = new IntSelector();
        this.shortNbOfPeriods = selector.startSelector("Short EMA");
        this.longNbOfPeriods = selector.startSelector("Long EMA");
        this.signalNbOfPeriods = selector.startSelector("Signal EMA");
    }

    @Override
    public StrategyResult execute(Symbol symbol, double tickerPrice) {
        return buyDecision(symbol, tickerPrice);
    }

    protected StrategyResult buyDecision(Symbol symbol, double tickerPrice) {
        computeParams(symbol);
        if (getCurrentMACD() > getCurrentSignal()) {
            return StrategyResult.BUY;
        } else {
            return StrategyResult.SELL;
        }
    }

    protected void computeParams(Symbol symbol) {
        getMacdAndSignalLines(symbol);
    }

    protected void getMacdAndSignalLines(Symbol symbol) {
        // To compute the {size} EMA, you normally need {size * 2 - 1} records,
        // but binance uses at least { 5 * size } to be more accurate.
        int recordsToFetch = this.longNbOfPeriods * 5 - 4;
        Double[] prices = exchange.getClosePrices(symbol, period.toString(), recordsToFetch);

        // Compute the short EMA (generally 12) and the long EMA (generally 26) used for the MACD line
        Double[] shortEMAS = Calculus.expMovingAvgesWithSize(prices, this.shortNbOfPeriods);
        Double[] longEMAs = Calculus.expMovingAvgesWithSize(prices, this.longNbOfPeriods);

        // Compute the MACD Line which is the subtraction of the longEMA from the shortEMA
        this.MACDLine = this.computeMACDLine(shortEMAS, longEMAs);

        // Compute the signal line which is the EMA9 of the MACD line (subtractions)
        this.signalLine = Calculus.expMovingAvgesWithSize(MACDLine, signalNbOfPeriods);
    }

    protected Double[] computeMACDLine(Double[] shortEMAs, Double[] longEMAs) {
        ArrayList<Double> MACDLine = new ArrayList<>();
        // Binance calculates the Signal with at least { 6 * signalNbOfPeriods }
        int recordsNeededForSignal = this.signalNbOfPeriods * 6 - 5;
        int lastIndexShortEMA = shortEMAs.length - 1;
        int lastIndexLongEMA = longEMAs.length - 1;
        for (int i=1; i <= recordsNeededForSignal; i++) {
            MACDLine.add(shortEMAs[lastIndexShortEMA - recordsNeededForSignal + i] - longEMAs[lastIndexLongEMA - recordsNeededForSignal + i]);
        }
        return MACDLine.toArray(Double[]::new);
    }

    @Override
    public void printCurrentStatus(HashMap<String, Double> balances, double tickerPrice) {
        System.out.println(currentStatus(balances, tickerPrice));
    }

    @Override
    public Period getPeriod() {
        return this.period;
    }

    protected String currentStatus(HashMap<String, Double> balances, double tickerPrice) {
        return "Base balance: " + balances.get("base") +
                "\nQuote balance: " + balances.get("quote") +
                "\nTicker " + tickerPrice +
                "\nMACD " + getCurrentMACD() +
                "\nSignal " + getCurrentSignal();
    }

    @Override
    public String describe() {
        return "Time Period: " + this.period +
                "\nShort Number of Periods: " + this.shortNbOfPeriods +
                "\nLong Number of Periods: " + this.longNbOfPeriods +
                "\nSignal Number of Periods: " + this.signalNbOfPeriods;
    }

    @Override
    public String toString() {
        return "Moving Average Convergence Divergence";
    }
}
