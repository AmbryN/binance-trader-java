package org.crypto.bot.classes.strategies;

import org.crypto.bot.enums.Symbol;
import org.crypto.bot.utils.Calculus;

public class EMAStrategy extends MovingAverage {

    public EMAStrategy() {
        super();
    }

    @Override
    protected void calculateMovingAvg(Symbol symbol) {
        // Binance uses at least { 5 * nbOfPeriods } to get the most accurate EMA
        Double[] closePrices = this.getClosePrices(symbol, period.toString(), this.nbOfPeriods * 5 - 4);
        Double[] emaList = Calculus.expMovingAvgesWithSize(closePrices, this.nbOfPeriods);
        this.movingAvg = emaList[(emaList.length -1)];
    }

    @Override
    public String toString() {
        return "Exp Moving Avg";
    }
}
