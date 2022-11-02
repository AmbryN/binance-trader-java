package com.binance.trader.runners;

import com.binance.trader.services.AsyncBalanceTickerService;
import com.binance.trader.classes.data.TraderConfig;
import com.binance.trader.classes.data.TransactionDecision;
import com.binance.trader.enums.StrategyResult;
import com.binance.trader.enums.Symbol;
import com.binance.trader.interfaces.Exchange;

import java.util.HashMap;

public class TransactionRunner implements Runnable {
    private final AsyncBalanceTickerService asyncBalanceTickerService;
    private final TraderConfig traderConfig;

    public TransactionRunner(TraderConfig traderConfig) {
        this.traderConfig = traderConfig;
        asyncBalanceTickerService = new AsyncBalanceTickerService(traderConfig);
    }

    @Override
    public void run() {
        HashMap<String, Double> balances = asyncBalanceTickerService.getBalances();
        Double tickerPrice = asyncBalanceTickerService.getTicker();

        Exchange exchange = traderConfig.getExchange();
        Symbol symbol = traderConfig.getSymbol();

        StrategyResult action = TransactionDecision.getDecision();
        if (action == StrategyResult.BUY && balances.get("quote") > symbol.MIN_QUOTE_TRANSACTION) {
            exchange.buy(symbol, tickerPrice, balances.get("quote"));
        } else if (action == StrategyResult.SELL && balances.get("base") > symbol.MIN_BASE_TRANSACTION) {
            exchange.sell(symbol, tickerPrice, balances.get("base"));
        }
        TransactionDecision.setDecision(StrategyResult.HOLD);
    }
}
