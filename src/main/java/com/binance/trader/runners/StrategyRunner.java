package com.binance.trader.runners;

import com.binance.trader.interfaces.Exchange;
import com.binance.trader.classes.data.TraderConfig;
import com.binance.trader.classes.data.TransactionDecision;
import com.binance.trader.enums.StrategyResult;
import com.binance.trader.enums.Symbol;
import com.binance.trader.interfaces.Strategy;

import java.util.HashMap;

public class StrategyRunner implements Runnable {
    private final TraderConfig traderConfig;

    public StrategyRunner(TraderConfig traderConfig) {
        this.traderConfig = traderConfig;
    }

    @Override
    public void run() {
        Exchange exchange = traderConfig.getExchange();
        Strategy strategy = traderConfig.getStrategy();
        Symbol symbol = traderConfig.getSymbol();

        HashMap<String, Double> balances = exchange.getBaseAndQuoteBalances(symbol);
        Double tickerPrice = exchange.getTickerPrice(symbol);

        StrategyResult result = strategy.execute(symbol, tickerPrice);
        strategy.printCurrentStatus(balances, tickerPrice);
        TransactionDecision.setDecision(result);
    }
}
