package com.binance.trader.classes.strategies;

import com.binance.trader.enums.Symbol;
import com.binance.trader.utils.Calculus;

import java.util.ArrayList;

public class EMAStrategy extends MovingAverage {

    public EMAStrategy() {
        super();
    }

    @Override
    protected void calculateMovingAvg(Symbol symbol) {
        // Binance uses at least { 5 * nbOfPeriods } to get the most accurate EMA
        Double[] closePrices = this.getClosePrices(symbol, period.asString(), this.nbOfPeriods * 5 - 4);
        Double[] emaList = Calculus.expMovingAvgesWithSize(closePrices, this.nbOfPeriods);
        this.movingAvg = emaList[(emaList.length -1)];
    }

    @Override
    public String toString() {
        return "Exp Moving Avg";
    }
}
