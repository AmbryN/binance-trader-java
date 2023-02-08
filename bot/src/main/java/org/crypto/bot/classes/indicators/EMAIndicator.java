package org.crypto.bot.classes.indicators;

import org.crypto.bot.utils.Calculus;

/**
 * Indicator representing an exponential moving average.
 * <a href="https://www.investopedia.com/ask/answers/122314/what-exponential-moving-average-ema-formula-and-how-ema-calculated.asp">
 *     https://www.investopedia.com/ask/answers/122314/what-exponential-moving-average-ema-formula-and-how-ema-calculated.asp
 * </a>
 */
public class EMAIndicator extends MAIndicator {
    public EMAIndicator() {}

    public EMAIndicator(Indicator indicator, int nbOfPeriods) {
        super(indicator, nbOfPeriods);
    }

    @Override
    public int getNbOfRecordsToFetch() {
        // To compute the {size} EMA, you normally need {size * 2 - 1} records,
        // but binance uses at least { 5 * size } to be more accurate.
        return this.nbOfPeriods * 5 - 4;
    }
    @Override
    protected double calculateMovingAvg(double[] closePrices) {
        double[] emaList = Calculus.expMovingAvgesWithSize(closePrices, this.nbOfPeriods);
        this.lastValue = emaList[(emaList.length - 1)];
        return this.lastValue;
    }

    @Override
    public String toString() {
        return "(Exp. Moving Average: " + nbOfPeriods + " - Current: " + lastValue + ")";
    }
}
