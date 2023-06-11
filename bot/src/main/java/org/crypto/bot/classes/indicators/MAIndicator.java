package org.crypto.bot.classes.indicators;

import org.jetbrains.annotations.Nullable;

public abstract class MAIndicator implements Indicator {

    protected Indicator indicator;
    protected int nbOfPeriods;

    protected double[] lastPricesUsedForComputation;
    protected double[] lastValues;

    protected MAIndicator() {}

    protected MAIndicator(Indicator indicator, int nbOfPeriods) {
        this.indicator = indicator;
        this.nbOfPeriods = nbOfPeriods;
    }

    public void setNbOfPeriods(int nbOfPeriods) {
        this.nbOfPeriods = nbOfPeriods;
    }

    public int getNbOfRecordsToFetch() {
        return this.nbOfPeriods;
    }

    @Override
    public double getLastValue(double[] closePrices) {
        double[] cachedValues = getFromCacheOrUpdatePricesUsedForComputation(closePrices);
        if (cachedValues != null) {
            return cachedValues[cachedValues.length - 1];
        }

        double[] values = this.indicator.getAllValues(closePrices);
        double[] avgs = calculateMovingAvgWithSize(values, this.nbOfPeriods);
        this.lastValues = avgs;
        return avgs[avgs.length - 1];
    }

    public double[] getAllValues(double[] closePrices) {
        double[] cachedValues = getFromCacheOrUpdatePricesUsedForComputation(closePrices);
        if (cachedValues != null) {
            return cachedValues;
        }

        double[] values = this.indicator.getAllValues(closePrices);
        double[] avgs = calculateMovingAvgWithSize(values, nbOfPeriods);
        this.lastValues = avgs;
        return avgs;
    }

    @Nullable
    private double[] getFromCacheOrUpdatePricesUsedForComputation(double[] closePrices) {
        if (closePrices == this.lastPricesUsedForComputation) {
            return this.lastValues;
        }
        this.lastPricesUsedForComputation = closePrices;
        return null;
    }

    protected abstract double[] calculateMovingAvgWithSize(double[] closePrices, int nbOfPeriods);
}
