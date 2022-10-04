package com.binance.trader.classes.strategies;

import java.util.ArrayList;

import com.binance.trader.classes.Kline;
import com.binance.trader.enums.Symbol;
import com.binance.trader.services.KlineService;
import com.binance.trader.utils.Calculus;

public class SMAStrategy extends MovingAverage {

    public SMAStrategy() {
        super();
    }

    protected double calculateMovingAvg(Symbol symbol) {
        KlineService klineService = new KlineService(this.client);

        ArrayList<Kline> klines = klineService.fetchKlines(symbol, this.period.asString(), this.nbOfPeriods);
        ArrayList<Double> prices = new ArrayList<>();

        klines.forEach((kline) -> prices.add(kline.getClosePrice()));
        
        return Calculus.simpleMovingAvg(prices);
    }

    @Override
    public String toString() {
        return "Simple Moving Avg";
    }
}
