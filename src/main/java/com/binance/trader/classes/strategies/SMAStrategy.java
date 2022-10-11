package com.binance.trader.classes.strategies;

import java.util.ArrayList;

import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.trader.classes.data.Kline;
import com.binance.trader.classes.selectors.IntSelector;
import com.binance.trader.classes.selectors.PeriodListSelector;
import com.binance.trader.enums.Period;
import com.binance.trader.enums.Symbol;
import com.binance.trader.services.KlineService;
import com.binance.trader.utils.Calculus;

public class SMAStrategy extends MovingAverage {

    public SMAStrategy() {
        super();
    }

    protected double calculateMovingAvg(Symbol symbol) {
        ArrayList<Double> closePrices = this.getClosePrices(symbol, this.nbOfPeriods);
        return Calculus.simpleMovingAvg(closePrices);
    }

    @Override
    public String toString() {
        return "Simple Moving Avg";
    }
}
