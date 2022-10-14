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

public class MACDStrategy implements Strategy {
    protected Exchange exchange;
    protected Period period;
    protected int shortNbOfPeriods;
    protected int longNbOfPeriods;
    protected int signalNbOfPeriods;

    public MACDStrategy() {}

    public void setShortNbOfPeriods(int shortNbOfPeriods) {
        this.shortNbOfPeriods = shortNbOfPeriods;
    }

    public void setLongNbOfPeriods(int longNbOfPeriods) {
        this.longNbOfPeriods = longNbOfPeriods;
    }

    public void setSignalNbOfPeriods(int signalNbOfPeriods) {
        this.signalNbOfPeriods = signalNbOfPeriods;
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
    public StrategyResult execute(Symbol symbol, HashMap<String, Double> balances, double tickerPrice) {
        HashMap<String, ArrayList<Double>> lines = this.getMacdAndSignalLines(symbol);
        ArrayList<Double> MACDLine = lines.get("macd");
        ArrayList<Double> signalLine = lines.get("signal");
        double newMACD = MACDLine.get(MACDLine.size() - 1);
        double lastMACD = MACDLine.get(MACDLine.size() - 2);
        double newSignal = signalLine.get(signalLine.size() - 1);
        double lastSignal = signalLine.get(signalLine.size() - 2);

        CrossingDirection crossing = this.computeCrossingDirection(newSignal, newMACD, lastSignal, lastMACD);

        System.out.println("Base balance: " + balances.get("base") +
                "\nQuote balance: " + balances.get("quote") +
                "\nTicker " + tickerPrice +
                "\nCrossing " + crossing +
                "\nSignal " + newSignal +
                "\nMACD " + newMACD);

        if (crossing == CrossingDirection.UP && balances.get("quote") > symbol.MIN_QUOTE_TRANSACTION) {
            return StrategyResult.BUY;
        } else if (crossing == CrossingDirection.DOWN && balances.get("base") > symbol.MIN_BASE_TRANSACTION) {
            return StrategyResult.SELL;
        }
        return StrategyResult.NONE;
    }

    protected HashMap<String, ArrayList<Double>> getMacdAndSignalLines(Symbol symbol) {
        // To compute the {size} EMA, you always need {size * 2 - 1} records
        int recordsToFetch = this.longNbOfPeriods + this.signalNbOfPeriods * 2 - 2;
        ArrayList<Double> prices = exchange.getClosePrices(symbol, period.asString(), recordsToFetch);

        // Compute the short EMA (generally 12) and the long EMA (generally 26) used for the MACD line
        ArrayList<Double> shortEMAS = Calculus.expMovingAvgesWithSize(prices, this.shortNbOfPeriods);
        ArrayList<Double> longEMAs = Calculus.expMovingAvgesWithSize(prices, this.longNbOfPeriods);

        // Compute the MACD Line which is the subtraction of the longEMA from the shortEMA
        ArrayList<Double> MACDLine = this.computeMACDLine(shortEMAS, longEMAs);

        // Compute the signal line which is the EMA9 of the MACD line (subtractions)
        ArrayList<Double> signalLine = Calculus.expMovingAvgesWithSize(MACDLine, signalNbOfPeriods);

        HashMap<String, ArrayList<Double>> lines = new HashMap<>();
        lines.put("macd", MACDLine);
        lines.put("signal", signalLine);
        return lines;
    }

    protected ArrayList<Double> computeMACDLine(ArrayList<Double> shortEMAs, ArrayList<Double> longEMAs) {
        ArrayList<Double> MACDLine = new ArrayList<>();
        int recordsNeededForSignal = this.signalNbOfPeriods * 2 - 1;
        int lastIndexShortEMA = shortEMAs.size() - 1;
        int lastIndexLongEMA = longEMAs.size() - 1;
        for (int i=1; i <= recordsNeededForSignal; i++) {
            MACDLine.add(shortEMAs.get(lastIndexShortEMA - recordsNeededForSignal + i) - longEMAs.get(lastIndexLongEMA - recordsNeededForSignal + i));
        }
        return MACDLine;
    }
    private CrossingDirection computeCrossingDirection(double newSignal, double newMACD, double lastSignal, double lastMACD) {
        if (newMACD >= newSignal && lastMACD < lastSignal) {
            return CrossingDirection.UP;
        } else if (newMACD <= newSignal && lastMACD > lastSignal) {
            return CrossingDirection.DOWN;
        } else {
            return CrossingDirection.NONE;
        }
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

    protected void setPeriod(Period period) {
        this.period = period;
    }
}
