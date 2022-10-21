package com.binance.trader;

import ch.qos.logback.classic.Logger;
import com.binance.trader.classes.facade.BinanceFacade;
import com.binance.trader.classes.handlers.Try;
import com.binance.trader.classes.selectors.*;
import com.binance.trader.enums.StrategyResult;
import com.binance.trader.enums.Symbol;
import com.binance.trader.exceptions.BinanceTraderException;
import com.binance.trader.interfaces.Exchange;
import com.binance.trader.interfaces.Strategy;
import com.binance.trader.utils.Logging;

import java.util.HashMap;

public class Trader implements Runnable {
    private final static int MAX_RECONNECT_TRIES = 5;
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

    @Override
    public void run()  {
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
        while (!Thread.interrupted()) {
            start();
        }
    }

    private void start() {
        Try.toRunNbOfTimes(this::trade, MAX_RECONNECT_TRIES);
    }

    private void trade() {
        HashMap<String, Double> balances = exchange.getBaseAndQuoteBalances(symbol);
        double tickerPrice = exchange.getTickerPrice(symbol);
        StrategyResult result = strategy.execute(symbol, balances, tickerPrice);

        if (result == StrategyResult.BUY && balances.get("quote") > symbol.MIN_QUOTE_TRANSACTION) {
            exchange.buy(symbol, tickerPrice, balances.get("quote"));
        } else if (result == StrategyResult.SELL && balances.get("base") > symbol.MIN_BASE_TRANSACTION) {
            exchange.sell(symbol, tickerPrice, balances.get("base"));
        }
    }
}
