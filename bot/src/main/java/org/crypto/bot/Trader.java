package org.crypto.bot;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.crypto.bot.classes.factory.StrategyFactory;
import org.crypto.bot.classes.facade.BinanceExchange;
import org.crypto.bot.classes.handlers.Try;
import org.crypto.bot.classes.selectors.*;
import org.crypto.bot.enums.Period;
import org.crypto.bot.enums.StrategyName;
import org.crypto.bot.enums.StrategyResult;
import org.crypto.bot.enums.Symbol;
import org.crypto.bot.interfaces.Exchange;
import org.crypto.bot.interfaces.Strategy;
import org.crypto.bot.utils.Logging;

import java.util.HashMap;

/**
 * Represents a trading bot.
 * It needs a symbol to trade, a strategy to apply, and an exchange to trade on.
 */
public class Trader implements Runnable {
    private final static int MAX_RECONNECT_TRIES = 5;
    private final Exchange exchange;
    private final Strategy strategy;
    private final Symbol symbol;
    private double lastBuyingPrice;

    public Trader(Exchange exchange, Symbol symbol, Strategy strategy) {
        this.exchange = exchange;
        this.symbol = symbol;
        this.strategy = strategy;
        this.lastBuyingPrice = 0.;
    }

    /**
     * Starts the Trader, which runs indefinitely until killed.
     * The Trader will try to run a tick (an iteration of the strategy)
     * and again after a sleep of the strategy's selected computing period (in ms) divided by 120,
     * i.e., for a selected period of one hour (3600000 ms), the Trader will wait for 3600000 / 120 = 30000 ms (30 s).
     */
    @Override
    public void run()  {
        while (!Thread.interrupted()) {
            Long period = strategy.getPeriod().toMillis();
            Try.toRunTimes(this::tick, MAX_RECONNECT_TRIES);
            try {
                Thread.sleep(period / 120);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     *  Computes on iteration of the strategy.
     */
    private void tick() {
        HashMap<String, Double> balances = exchange.getBaseAndQuoteBalances(symbol);
        double tickerPrice = exchange.getTicker(symbol);

        Period period = strategy.getPeriod();
        int recordsToFetch = strategy.getAmountOfRecordsToFetch();
        double[] closePrices = exchange.getClosePrices(symbol, period, recordsToFetch);

        StrategyResult result = strategy.execute(tickerPrice, closePrices);

        System.out.println(
                "Base balance: " + balances.get("base") +
                "\nQuote balance: " + balances.get("quote") +
                "\nTicker: " + tickerPrice +
                strategy.getCurrentStatus());

        if (result == StrategyResult.BUY && balances.get("quote") > symbol.MIN_QUOTE_TRANSACTION) {
            exchange.buy(symbol, tickerPrice, balances.get("quote"));
            lastBuyingPrice = tickerPrice;
        } else if ((result == StrategyResult.SELL || tickerPrice < lastBuyingPrice * 0.99)
                && balances.get("base") > symbol.MIN_BASE_TRANSACTION) {
            exchange.sell(symbol, tickerPrice, balances.get("base"));
            lastBuyingPrice = 0.;
        }
    }

    @Override
    public String toString() {
        return String.format("On: %s\nSymbol:  %s\nStrategy: %s\n", exchange, symbol, strategy.describe());
    }


    /**
     * Used as a point of entry when running the bot in console mode.
     */
    public static void main( String[] args ) {
        Logger logger = Logging.getInstance();
        logger.setLevel(Level.WARN);

        Symbol symbol;
        Strategy strategy;
        Trader trader = null;

        boolean start = false;
        while(!start) {
            symbol = new SymbolListSelector().startSelector();
            StrategyName strategyName = new StrategyListSelector().startSelector();
            strategy = StrategyFactory.getStrategyBuilderFor(strategyName);

            trader = new Trader(new BinanceExchange(), symbol, strategy);

            System.out.println("=== SUMMARY ===\nYou want to trade:");
            System.out.println(trader);

            start = new YesNoSelector().startSelector();
        }

        trader.run();
    }
}
