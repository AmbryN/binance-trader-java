package com.binance.trader.classes.strategies;

import com.binance.trader.enums.Symbol;
import com.binance.trader.utils.Calculus;

import java.util.ArrayList;

public class EMAStrategy extends MovingAverage {

    public EMAStrategy() {
        super();
    }

    @Override
    protected double calculateMovingAvg(Symbol symbol) {
        Double[] closePrices = this.getClosePrices(symbol, period.asString(), this.nbOfPeriods * 2 - 1);
        Double[] emaList = Calculus.expMovingAvgesWithSize(closePrices, this.nbOfPeriods);
        return emaList[(emaList.length -1)];
    }

    @Override
    public String toString() {
        return "Exp Moving Avg";
    }
}
