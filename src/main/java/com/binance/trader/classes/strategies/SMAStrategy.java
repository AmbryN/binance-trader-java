package com.binance.trader.classes.strategies;

import com.binance.trader.enums.Symbol;
import com.binance.trader.utils.Calculus;

public class SMAStrategy extends MovingAverage {

    public SMAStrategy() {
        super();
    }

    @Override
    protected void calculateMovingAvg(Symbol symbol) {
        Double[] closePrices = this.getClosePrices(symbol, period.toString(), this.nbOfPeriods);
        this.movingAvg = Calculus.simpleMovingAvg(closePrices);
    }

    @Override
    public String toString() {
        return "Simple Moving Avg";
    }
}
