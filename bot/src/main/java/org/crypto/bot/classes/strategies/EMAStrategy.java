package org.crypto.bot.classes.strategies;

import org.crypto.bot.enums.Symbol;
import org.crypto.bot.utils.Calculus;

public class EMAStrategy extends MovingAverage {


    public EMAStrategy() {
        super();
        // Binance uses at least { 5 * nbOfPeriods } to get the most accurate EMA
        this.nbOfRecordsToFetch = this.nbOfPeriods *  - 4;
    }

    @Override
    protected void calculateMovingAvg(double[] closePrices) {
        double[] emaList = Calculus.expMovingAvgesWithSize(closePrices, this.nbOfPeriods);
        this.movingAvg = emaList[(emaList.length -1)];
    }

    @Override
    public String toString() {
        return "Exp Moving Avg";
    }
}
