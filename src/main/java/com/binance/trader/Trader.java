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
         while (true) {
            int tries = 0;
            while (tries < MAX_RECONNECT_TRIES) {
                try {
                    HashMap<String, Double> balances = exchange.getBaseAndQuoteBalances(symbol);
                    double tickerPrice = exchange.getTickerPrice(symbol);
                    StrategyResult result = strategy.execute(symbol, balances, tickerPrice);

                    if (result == StrategyResult.BUY && balances.get("quote") > symbol.MIN_QUOTE_TRANSACTION) {
                        exchange.buy(symbol, tickerPrice, balances.get("quote"));
                    } else if (result == StrategyResult.SELL && balances.get("base") > symbol.MIN_BASE_TRANSACTION) {
                        exchange.sell(symbol, tickerPrice, balances.get("base"));
                    }
                    tries = 0;
                } catch (BinanceTraderException e) {
                    logger.error(e.getMessage(), e);
                    if (tries < MAX_RECONNECT_TRIES) {
                        tries++;
                        try {
                            logger.warn(String.format("Trying to reconnect [%d/%d].", tries, MAX_RECONNECT_TRIES));
                            Thread.sleep(5000);
                        } catch (InterruptedException i) {
                            logger.error("Interrupted while waiting: ", i);
                        }
                    }
                    if (tries == 5) {
                        throw e;
                    }
                }
            }
        }
    }
}
