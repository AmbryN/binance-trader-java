package org.crypto.bot.classes.strategies;

import org.crypto.bot.classes.selectors.IntSelector;
import org.crypto.bot.classes.selectors.PeriodListSelector;
import org.crypto.bot.interfaces.Strategy;
import org.crypto.bot.utils.Calculus;
import org.crypto.bot.enums.CrossingDirection;
import org.crypto.bot.enums.Period;
import org.crypto.bot.enums.StrategyResult;

import java.util.ArrayList;

public class MACDStrategy implements Strategy {
    private Period period;
    private int shortNbOfPeriods;
    private int longNbOfPeriods;
    private int signalNbOfPeriods;
    protected int nbOfRecordsToFetch;
    private double[] MACDLine;
    private double[] signalLine;
    protected CrossingDirection crossingDirection;

    public MACDStrategy() {
        this.crossingDirection = CrossingDirection.NONE;
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

    public void init() {
        this.period = new PeriodListSelector().startSelector();
        IntSelector selector = new IntSelector();
        this.shortNbOfPeriods = selector.startSelector("Short EMA");
        this.longNbOfPeriods = selector.startSelector("Long EMA");
        this.signalNbOfPeriods = selector.startSelector("Signal EMA");
        // To compute the {size} EMA, you normally need {size * 2 - 1} records,
        // but binance uses at least { 5 * size } to be more accurate.
        this.nbOfRecordsToFetch = this.longNbOfPeriods * 5 - 4;
    }

    @Override
    public StrategyResult execute(double tickerPrice, double[] closePrices) {
        return buyDecision(tickerPrice, closePrices);
    }

    @Override
    public int getAmountOfRecordsToFetch() {
       return nbOfRecordsToFetch;
    }

    protected StrategyResult buyDecision(double tickerPrice, double[] closePrices) {
        computeParams(closePrices);
        if (getCurrentMACD() > getCurrentSignal()) {
            return StrategyResult.BUY;
        } else {
            return StrategyResult.SELL;
        }
    }

    protected void computeParams(double[] closePrices) {
        getMacdAndSignalLines(closePrices);
    }

    protected void getMacdAndSignalLines(double[] closePrices) {
        // Compute the short EMA (generally 12) and the long EMA (generally 26) used for the MACD line
        double[] shortEMAS = Calculus.expMovingAvgesWithSize(closePrices, this.shortNbOfPeriods);
        double[] longEMAs = Calculus.expMovingAvgesWithSize(closePrices, this.longNbOfPeriods);

        // Compute the MACD Line which is the subtraction of the longEMA from the shortEMA
        this.MACDLine = this.computeMACDLine(shortEMAS, longEMAs);

        // Compute the signal line which is the EMA9 of the MACD line (subtractions)
        this.signalLine = Calculus.expMovingAvgesWithSize(MACDLine, signalNbOfPeriods);
    }

    protected double[] computeMACDLine(double[] shortEMAs, double[] longEMAs) {
        ArrayList<Double> MACDLine = new ArrayList<>();
        // Binance calculates the Signal with at least { 6 * signalNbOfPeriods }
        int recordsNeededForSignal = this.signalNbOfPeriods * 6 - 5;
        int lastIndexShortEMA = shortEMAs.length - 1;
        int lastIndexLongEMA = longEMAs.length - 1;
        for (int i=1; i <= recordsNeededForSignal; i++) {
            MACDLine.add(shortEMAs[lastIndexShortEMA - recordsNeededForSignal + i] - longEMAs[lastIndexLongEMA - recordsNeededForSignal + i]);
        }
        return MACDLine.stream().mapToDouble(Double::doubleValue).toArray();
    }

    @Override
    public Period getPeriod() {
        return this.period;
    }

    public String getCurrentStatus() {
        return  "\nMACD " + getCurrentMACD() +
                "\nSignal " + getCurrentSignal();
    }

    @Override
    public String describe() {
        return this +
                "\nTime Period: " + this.period +
                "\n-> Short Number of Periods: " + this.shortNbOfPeriods +
                "\n-> Long Number of Periods: " + this.longNbOfPeriods +
                "\n-> Signal Number of Periods: " + this.signalNbOfPeriods;
    }

    @Override
    public String toString() {
        return "Moving Average Convergence Divergence";
    }
}
