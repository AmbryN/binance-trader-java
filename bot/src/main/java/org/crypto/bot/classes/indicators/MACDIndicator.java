package org.crypto.bot.classes.indicators;

import org.crypto.bot.utils.Calculus;

import java.util.ArrayList;

/**
 * Indicator representing the Moving Average Convergence Divergence
 * <a href="https://www.investopedia.com/ask/answers/122414/what-moving-average-convergence-divergence-macd-formula-and-how-it-calculated.asp">
 *     https://www.investopedia.com/ask/answers/122414/what-moving-average-convergence-divergence-macd-formula-and-how-it-calculated.asp
 * </a>
 */
public class MACDIndicator implements Indicator {
    private int shortNbOfPeriods;
    private int longNbOfPeriods;
    private double lastValue;
    private Indicator indicator;

    public MACDIndicator() {}

    public MACDIndicator(Indicator indicator, int shortNbOfPeriods, int longNbOfPeriods) {
        this.indicator = indicator;
        this.shortNbOfPeriods = shortNbOfPeriods;
        this.longNbOfPeriods = longNbOfPeriods;
    }

    public void setShortNbOfPeriods(int shortNbOfPeriods) {
        this.shortNbOfPeriods = shortNbOfPeriods;
    }

    public void setLongNbOfPeriods(int longNbOfPeriods) {
        this.longNbOfPeriods = longNbOfPeriods;
    }

    @Override
    public double getLastValue(double[] closePrices) {
        double[] values = this.indicator.getAllValues(closePrices);
        // Compute the short EMA (generally 12) and the long EMA (generally 26) used for the MACD line
        double[] shortEMAS = Calculus.expMovingAvgesWithSize(values, this.shortNbOfPeriods);
        double[] longEMAs = Calculus.expMovingAvgesWithSize(values, this.longNbOfPeriods);

        // Compute the MACD Line which is the subtraction of the longEMA from the shortEMA
        double[] MACDLine = this.computeMACDLine(shortEMAS, longEMAs);
        this.lastValue = MACDLine[MACDLine.length - 1];
        return this.lastValue;
    }

    public double[] getAllValues(double[] closePrices) {
        double[] values = this.indicator.getAllValues(closePrices);
        // Compute the short EMA (generally 12) and the long EMA (generally 26) used for the MACD line
        double[] shortEMAS = Calculus.expMovingAvgesWithSize(values, this.shortNbOfPeriods);
        double[] longEMAs = Calculus.expMovingAvgesWithSize(values, this.longNbOfPeriods);

        // Compute the MACD Line which is the subtraction of the longEMA from the shortEMA
        return this.computeMACDLine(shortEMAS, longEMAs);
    }

    protected double[] computeMACDLine(double[] shortEMAs, double[] longEMAs) {
        int longNbOfValues = longEMAs.length;
        int shortNbOfValues = shortEMAs.length;
        int lastIndexLongEMA = longEMAs.length - 1;
        double[] MACDLine = new double[longNbOfValues];
        /* Calculates the last longNbOfValues values of the MACD Line.
           Since there are more short EMAs as long EMAs available, we only take the last computable values.
         */
        for (int i=lastIndexLongEMA; i > lastIndexLongEMA - longNbOfValues; i--) {
            double result = (shortEMAs[i + shortNbOfValues - longNbOfValues] - longEMAs[i]);
            MACDLine[i] = result;
        }
        return MACDLine;
    }

    @Override
    public int getNbOfRecordsToFetch() {
        // To compute the {size} EMA, you normally need {size * 2 - 1} records,
        // but binance uses at least { 5 * size } to be more accurate.
        return this.longNbOfPeriods * 5 - 4;
    }

    @Override
    public String toString() {
        return  "(MACD: Short " + this.shortNbOfPeriods + " / Long " + this.longNbOfPeriods + " - Current: " + lastValue + ")";
    }
}
