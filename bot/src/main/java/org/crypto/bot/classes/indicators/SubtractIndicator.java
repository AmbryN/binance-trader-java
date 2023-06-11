package org.crypto.bot.classes.indicators;

import org.jetbrains.annotations.Nullable;

public class SubtractIndicator implements Indicator {

    private Indicator first;
    private Indicator second;

    protected double[] lastPricesUsedForComputation;
    protected double[] lastValues;

    public SubtractIndicator() {}

    public SubtractIndicator(Indicator first, Indicator second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public double getLastValue(double[] closePrices) {
        double[] cachedValues = getFromCacheOrUpdatePricesUsedForComputation(closePrices);
        if (cachedValues != null) {
            return cachedValues[cachedValues.length - 1];
        }

       return this.first.getLastValue(closePrices) - this.second.getLastValue(closePrices);
    }

    @Override
    public double[] getAllValues(double[] prices) {
        double[] cachedValues = getFromCacheOrUpdatePricesUsedForComputation(prices);
        if (cachedValues != null) {
            return cachedValues;
        }

        double[] firstValues = this.first.getAllValues(prices);
        double[] secondValues = this.second.getAllValues(prices);
        double[] result = new double[Math.min(firstValues.length, secondValues.length)];

        for (int i = result.length - 1; i >= 0; i--) {
            result[i] = firstValues[i] - secondValues[i];
        }
        this.lastValues = result;
        return result;
    }

    @Nullable
    private double[] getFromCacheOrUpdatePricesUsedForComputation(double[] closePrices) {
        if (closePrices == this.lastPricesUsedForComputation) {
            return this.lastValues;
        }
        this.lastPricesUsedForComputation = closePrices;
        return null;
    }

    @Override
    public int getNbOfRecordsToFetch() {
        return Math.max(first.getNbOfRecordsToFetch(), second.getNbOfRecordsToFetch());
    }

    @Override
    public String toString() {
        return  "(" + first + " MINUS " + second + "";
    }
}
