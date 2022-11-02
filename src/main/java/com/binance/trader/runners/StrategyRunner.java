package com.binance.trader.runners;

import com.binance.trader.TraderConfig;
import com.binance.trader.classes.data.TransactionDecision;
import com.binance.trader.classes.handlers.Try;
import com.binance.trader.enums.StrategyResult;
import com.binance.trader.enums.Symbol;
import com.binance.trader.interfaces.Exchange;
import com.binance.trader.interfaces.Strategy;

import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class StrategyRunner implements Runnable {
    private Exchange exchange;
    private Symbol symbol;
    private Strategy strategy;
    private ExecutorService es;

    public StrategyRunner(TraderConfig traderConfig) {
        this.exchange = traderConfig.getExchange();
        this.symbol = traderConfig.getSymbol();
        this.strategy = traderConfig.getStrategy();
        this.es = Executors.newFixedThreadPool(2);
    }

    @Override
    public void run() {
        // Use Two Threads to get balances and ticker at the same time
        Future<HashMap<String, Double>> balancesFuture = es.submit(() -> exchange.getBaseAndQuoteBalances(symbol));
        Future<Double> tickerPriceFuture = es.submit(() -> exchange.getTickerPrice(symbol));

        // Unwrap the results of the Futures
        Optional<Double> tickerOption = Try.toGet(tickerPriceFuture::get);
        double tickerPrice = tickerOption.get();

        Optional<HashMap<String, Double>> balancesOption = Try.toGet(balancesFuture::get);
        HashMap<String, Double> balances = balancesOption.get();

        // Execute Strategy
        StrategyResult result = strategy.execute(symbol, tickerPrice);
        strategy.printCurrentStatus(balances, tickerPrice);
        TransactionDecision.setDecision(result);
    }
}
