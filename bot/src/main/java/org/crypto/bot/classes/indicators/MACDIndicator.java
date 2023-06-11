package org.crypto.bot.classes.indicators;

import org.crypto.bot.utils.Calculus;
import org.jetbrains.annotations.Nullable;

/**
 * Indicator representing the Moving Average Convergence Divergence
 * <a href="https://www.investopedia.com/ask/answers/122414/what-moving-average-convergence-divergence-macd-formula-and-how-it-calculated.asp">
 *     https://www.investopedia.com/ask/answers/122414/what-moving-average-convergence-divergence-macd-formula-and-how-it-calculated.asp
 * </a>
 */
public class MACDIndicator implements Indicator {
    private int shortNbOfPeriods;
    private int longNbOfPeriods;
    private double[] lastValues;
    private Indicator indicator;
    // This field is used to avoid recomputing the MACD line when the last prices used for the computation are the same.
    private double[] lastPricesUsedForComputation;

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
        double[] lastValues = getFromCacheOrUpdatePricesUsedForComputation(closePrices);
        if (lastValues != null) return lastValues[lastValues.length - 1];

        double[] values = this.indicator.getAllValues(closePrices);
        // Compute the short EMA (generally 12) and the long EMA (generally 26) used for the MACD line
        double[] shortEMAS = Calculus.expMovingAvgesWithSize(values, this.shortNbOfPeriods);
        double[] longEMAs = Calculus.expMovingAvgesWithSize(values, this.longNbOfPeriods);

        // Compute the MACD Line which is the subtraction of the longEMA from the shortEMA
        this.lastValues = this.computeMACDLine(shortEMAS, longEMAs);
        return this.lastValues[this.lastValues.length - 1];
    }

    public double[] getAllValues(double[] closePrices) {
        double[] lastValues = getFromCacheOrUpdatePricesUsedForComputation(closePrices);
        if (lastValues != null) return lastValues;

        double[] values = this.indicator.getAllValues(closePrices);
        // Compute the short EMA (generally 12) and the long EMA (generally 26) used for the MACD line
        double[] shortEMAS = Calculus.expMovingAvgesWithSize(values, this.shortNbOfPeriods);
        double[] longEMAs = Calculus.expMovingAvgesWithSize(values, this.longNbOfPeriods);

        // Compute the MACD Line which is the subtraction of the longEMA from the shortEMA
        this.lastValues = this.computeMACDLine(shortEMAS, longEMAs);
        return this.lastValues;
    }

    @Nullable
    private double[] getFromCacheOrUpdatePricesUsedForComputation(double[] closePrices) {
        if (closePrices == this.lastPricesUsedForComputation) {
            return this.lastValues;
        }
        this.lastPricesUsedForComputation = closePrices;
        return null;
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
        return  "(MACD: Short " + this.shortNbOfPeriods + " / Long " + this.longNbOfPeriods + " - Current: " + lastValues[lastValues.length - 1] + ")";
    }
}
