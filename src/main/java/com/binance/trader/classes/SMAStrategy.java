package com.binance.trader.classes;

import java.util.ArrayList;

import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.trader.enums.Period;
import com.binance.trader.enums.Symbol;
import com.binance.trader.intefaces.Strategy;
import com.binance.trader.services.AccountInfoService;
import com.binance.trader.services.KlineService;
import com.binance.trader.services.OrderService;
import com.binance.trader.services.TickerService;
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
        
        return Calculus.calculateAvg(prices);
    }

    @Override
    public String toString() {
        return "Standard Moving Avg";
    }
}
