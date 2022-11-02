package com.binance.trader.services;

import com.binance.trader.classes.data.TraderConfig;
import com.binance.trader.enums.Symbol;
import com.binance.trader.interfaces.Exchange;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AsyncBalanceTickerService {
    private final Exchange exchange;
    private final Symbol symbol;
    private static final ExecutorService es = Executors.newFixedThreadPool(2);
    private final Future<HashMap<String, Double>> balancesFuture;
    private final Future<Double> tickerPriceFuture;


    public AsyncBalanceTickerService(TraderConfig traderConfig) {
        this.exchange = traderConfig.getExchange();
        this.symbol = traderConfig.getSymbol();
        balancesFuture = es.submit(() -> exchange.getBaseAndQuoteBalances(symbol));
        tickerPriceFuture = es.submit(() -> exchange.getTickerPrice(symbol));
    }

    public HashMap<String, Double> getBalances() {
        try {
            return balancesFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public Double getTicker() {
        try {
            return tickerPriceFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
