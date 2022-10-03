package com.binance.trader.classes;

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
        KlineService klineService = new KlineService(this.client);

        ArrayList<Kline> klines = klineService.fetchKlines(symbol, this.period.asString(), this.nbOfPeriods * 2);
        ArrayList<Double> prices = new ArrayList<>();

        klines.forEach((kline) -> prices.add(kline.getClosePrice()));
        
        return Calculus.calculateExpAvg(prices);
    }

    @Override
    public String toString() {
        return "Exponential Moving Avg";
    }
}
