package org.crypto.bot.classes.indicators;

import org.crypto.bot.enums.Period;
import org.crypto.bot.utils.Calculus;

public class EMAIndicator extends MovingAverageIndicator {

    public EMAIndicator() {}

    @Override
    public int getNbOfRecordsToFetch() {
        // To compute the {size} EMA, you normally need {size * 2 - 1} records,
        // but binance uses at least { 5 * size } to be more accurate.
        return this.nbOfPeriods * 5 - 4;
    }
    @Override
    protected double calculateMovingAvg(double[] closePrices) {
        double[] emaList = Calculus.expMovingAvgesWithSize(closePrices, this.nbOfPeriods);
        return emaList[(emaList.length - 1)];
    }

    @Override
    public String toString() {
        return "Exp. Moving Average";
    }

    @Override
    public String describe() {
        return this + super.describe();
    }
}
