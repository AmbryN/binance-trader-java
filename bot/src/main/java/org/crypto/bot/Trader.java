package org.crypto.bot;

import org.crypto.bot.classes.facade.BinanceFacade;
import org.crypto.bot.classes.handlers.Try;
import org.crypto.bot.classes.selectors.StrategyListSelector;
import org.crypto.bot.classes.selectors.SymbolListSelector;
import org.crypto.bot.classes.selectors.YesNoSelector;
import org.crypto.bot.enums.StrategyResult;
import org.crypto.bot.enums.Symbol;
import org.crypto.bot.interfaces.Exchange;
import org.crypto.bot.interfaces.Strategy;

import java.util.HashMap;

public class Trader implements Runnable {
    private final static int MAX_RECONNECT_TRIES = 5;
    private final Exchange exchange;
    private Strategy strategy;
    private Symbol symbol;
    private double lastBuyingPrice;

    public Trader() {
        this.exchange = new BinanceFacade();
        this.lastBuyingPrice = 0.;
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
        Long period = strategy.getPeriod().toMillis();
        while (!Thread.interrupted()) {
            Try.toRunTimes(this::trade, MAX_RECONNECT_TRIES);
            try {
                Thread.sleep(period / 120);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void trade() {
        HashMap<String, Double> balances = exchange.getBaseAndQuoteBalances(symbol);
        double tickerPrice = exchange.getTicker(symbol);
        StrategyResult result = strategy.execute(symbol, tickerPrice);
        strategy.printCurrentStatus(balances, tickerPrice);

        if (result == StrategyResult.BUY && balances.get("quote") > symbol.MIN_QUOTE_TRANSACTION) {
            exchange.buy(symbol, tickerPrice, balances.get("quote"));
            lastBuyingPrice = tickerPrice;
        } else if ((result == StrategyResult.SELL || tickerPrice < lastBuyingPrice * 0.99)
                && balances.get("base") > symbol.MIN_BASE_TRANSACTION) {
            exchange.sell(symbol, tickerPrice, balances.get("base"));
            lastBuyingPrice = 0.;
        }
    }
}
