package com.binance.trader;

import ch.qos.logback.classic.Logger;
import com.binance.trader.classes.facade.BinanceFacade;
import com.binance.trader.classes.selectors.*;
import com.binance.trader.enums.StrategyResult;
import com.binance.trader.enums.Symbol;
import com.binance.trader.exceptions.BinanceTraderException;
import com.binance.trader.interfaces.Exchange;
import com.binance.trader.interfaces.Strategy;
import com.binance.trader.utils.Logging;

import java.util.HashMap;

public class Trader {
    private final static Logger logger = Logging.getInstance();
    private final Exchange exchange;
    private Strategy strategy;
    private Symbol symbol;

    public Trader() {
        this.exchange = new BinanceFacade();
    }

    public void init() {
        this.symbol = new SymbolListSelector().startSelector();
        this.strategy = new StrategyListSelector().startSelector();
        strategy.init(exchange);
    }

    public void start() {
        boolean start = false;
        while(!start) {
            this.init();
            System.out.println("=== SUMMARY === " +
                    "\nYou want to trade: " +
                    "\nSymbol: " + symbol +
                    "\nStrategy: " + strategy +
                    "\n" + strategy.describe());

            start = new YesNoSelector().startSelector();
        }
        trade();
    }

    private void trade() {
        try {
            HashMap<String, Double> balances = exchange.getBalances(symbol);
            while (true) {
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
        } catch (BinanceTraderException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
