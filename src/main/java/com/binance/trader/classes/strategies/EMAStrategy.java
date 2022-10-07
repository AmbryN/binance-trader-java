package com.binance.trader.classes.strategies;

import com.binance.trader.classes.data.Kline;
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

        ArrayList<Kline> klines = klineService.fetchKlines(symbol, this.period.asString(), this.nbOfPeriods * 2 - 1);
        ArrayList<Double> prices = new ArrayList<>();

        klines.forEach((kline) -> prices.add(kline.getClosePrice()));
        
        ArrayList<Double> EMAsList = Calculus.expMovingAvg(prices);
        return EMAsList.get(EMAsList.size() -1);
    }

    @Override
    public String toString() {
        return "Exp Moving Avg";
    }
}
