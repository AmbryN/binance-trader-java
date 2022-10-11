package com.binance.trader.classes.strategies;

import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.trader.classes.data.AccountInfo;
import com.binance.trader.classes.data.Kline;
import com.binance.trader.classes.selectors.IntSelector;
import com.binance.trader.classes.selectors.PeriodListSelector;
import com.binance.trader.enums.Period;
import com.binance.trader.enums.StrategyResult;
import com.binance.trader.enums.Symbol;
import com.binance.trader.intefaces.Strategy;
import com.binance.trader.services.AccountInfoService;
import com.binance.trader.services.KlineService;
import com.binance.trader.services.OrderService;
import com.binance.trader.services.TickerService;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class MovingAverage implements Strategy {
    protected SpotClientImpl client;
    protected Period period;
    protected int nbOfPeriods;

    protected MovingAverage(SpotClientImpl client) {
        this.client = client;
        this.period = new PeriodListSelector().startSelector();
        this.nbOfPeriods = new IntSelector().startSelector("Moving Average");
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public void setNbOfPeriods(int nbOfPeriods) {
        this.nbOfPeriods = nbOfPeriods;
    }

    @Override
    public StrategyResult execute(Symbol symbol, HashMap<String, Double> balances, double tickerPrice) {
        double movingAvg = this.calculateMovingAvg(symbol);

        System.out.println("Base balance: " + balances.get("base") +
                "\nQuote balance: " + balances.get("quote") +
                "\nTicker " + tickerPrice +
                "\nExpMAvg " + movingAvg);

        if (tickerPrice > movingAvg && balances.get("quote") > symbol.MIN_QUOTE_TRANSACTION) {
            return StrategyResult.BUY;
        } else if (tickerPrice < movingAvg && balances.get("base") > symbol.MIN_BASE_TRANSACTION) {
            return StrategyResult.SELL;
        }
        return StrategyResult.NONE;
    }

    protected abstract double calculateMovingAvg(Symbol symbol);

    protected ArrayList<Double> getClosePrices(Symbol symbol, int nbOfRecordsToFetch) {
        KlineService klineService = new KlineService(this.client);
        ArrayList<Kline> klines = klineService.fetchKlines(symbol, this.period.asString(), nbOfRecordsToFetch);
        ArrayList<Double> closePrices = new ArrayList<>();

        klines.forEach((kline) -> closePrices.add(kline.getClosePrice()));
        return closePrices;
    }

    public abstract String toString();

    @Override
    public String describe() {
        return "Time Period: " + this.period +
                "\n Number of Periods: " + this.nbOfPeriods;
    }
}
