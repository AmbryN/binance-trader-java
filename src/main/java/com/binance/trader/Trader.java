package com.binance.trader;

import com.binance.trader.classes.facade.BinanceFacade;
import com.binance.trader.classes.selectors.*;
import com.binance.trader.enums.StrategyResult;
import com.binance.trader.enums.Symbol;
import com.binance.trader.intefaces.Exchange;
import com.binance.trader.intefaces.Strategy;

import java.util.HashMap;

public class Trader {
    private final Exchange exchange;
    private boolean start;
    private Strategy strategy;
    private Symbol symbol;

    public Trader() {
        this.exchange = new BinanceFacade();
        this.symbol = new SymbolListSelector().startSelector();
        this.strategy = new StrategyListSelector().startSelector();
        this.start = false;
    }

    public void start() {
        while(!start) {
            strategy.init(exchange);

            System.out.println("=== SUMMARY === " +
                    "\nYou want to trade: " +
                    "\nSymbol: " + symbol +
                    "\nStrategy: " + strategy +
                    "\n" + strategy.describe());

            start = new YesNoSelector().startSelector();
            if (!start) restart();
        }

        trade();
    }

    private void restart() {
        this.symbol = new SymbolListSelector().startSelector();
        this.strategy = new StrategyListSelector().startSelector();
    }

    private void trade() {
        HashMap<String, Double> balances = exchange.getBalances(symbol);
        while(true) {
            double tickerPrice = exchange.getTickerPrice(symbol);
            StrategyResult result = strategy.execute(symbol, balances, tickerPrice);

            if (result == StrategyResult.BUY) {
                exchange.buy(symbol, tickerPrice, balances.get("quote"));
                balances = exchange.getBalances(symbol);
            } else if (result == StrategyResult.SELL) {
                exchange.sell(symbol, tickerPrice, balances.get("base"));
                balances = exchange.getBalances(symbol);
            }
        }
    }
}
