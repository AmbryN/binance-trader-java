package com.binance.trader.classes.strategies;

import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.trader.classes.data.Kline;
import com.binance.trader.enums.Period;
import com.binance.trader.enums.Symbol;
import com.binance.trader.services.KlineService;
import com.binance.trader.utils.Calculus;

import java.util.ArrayList;

public class EMAStrategy extends MovingAverage {

    public EMAStrategy() {
        super();
    }

    @Override
    protected double calculateMovingAvg(Symbol symbol) {
        ArrayList<Double> closePrices = this.getClosePrices(symbol, this.nbOfPeriods * 2 - 1);
        ArrayList<Double> emaList = Calculus.expMovingAvg(closePrices);
        return emaList.get(emaList.size() -1);
    }

    @Override
    public String toString() {
        return "Exp Moving Avg";
    }
}
