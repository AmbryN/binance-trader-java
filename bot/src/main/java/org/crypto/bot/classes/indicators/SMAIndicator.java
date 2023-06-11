package org.crypto.bot.classes.indicators;

import org.crypto.bot.utils.Calculus;

/**
 * Indicator representing a simple moving average (sum of all values / amount of values).
 */
public class SMAIndicator extends MAIndicator {

    public SMAIndicator() {}

    public SMAIndicator(Indicator indicator, int nbOfPeriods) {
        super(indicator, nbOfPeriods);
    }

    @Override
    protected double[] calculateMovingAvgWithSize(double[] closePrices, int nbOfPeriods) {
        return Calculus.simpleMovingAvgWithSize(closePrices, nbOfPeriods);
    }

    @Override
    public String toString() {
        return "(Simple Moving Average: " + nbOfPeriods + " - Current: " + lastValue +")";
    }
}
