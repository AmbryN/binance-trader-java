package com.binance.trader.runners;

import com.binance.trader.services.AsyncBalanceTickerService;
import com.binance.trader.classes.data.TraderConfig;
import com.binance.trader.classes.data.TransactionDecision;
import com.binance.trader.enums.StrategyResult;
import com.binance.trader.enums.Symbol;
import com.binance.trader.interfaces.Strategy;

import java.util.HashMap;

public class StrategyRunner implements Runnable {
    private final AsyncBalanceTickerService asyncBalanceTickerService;
    private final TraderConfig traderConfig;

    public StrategyRunner(TraderConfig traderConfig) {
        this.traderConfig = traderConfig;
        asyncBalanceTickerService = new AsyncBalanceTickerService(traderConfig);
    }

    @Override
    public void run() {
        HashMap<String, Double> balances = asyncBalanceTickerService.getBalances();
        Double tickerPrice = asyncBalanceTickerService.getTicker();

        Strategy strategy = traderConfig.getStrategy();
        Symbol symbol = traderConfig.getSymbol();

        StrategyResult result = strategy.execute(symbol, tickerPrice);
        strategy.printCurrentStatus(balances, tickerPrice);
        TransactionDecision.setDecision(result);
    }
}
