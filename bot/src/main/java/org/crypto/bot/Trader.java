package org.crypto.bot;

import org.crypto.bot.classes.handlers.Try;
import org.crypto.bot.enums.Period;
import org.crypto.bot.enums.StrategyResult;
import org.crypto.bot.enums.Symbol;
import org.crypto.bot.classes.exchange.Exchange;
import org.crypto.bot.classes.strategies.Strategy;

import java.util.HashMap;

/**
 * Represents a trading bot.
 * It needs a symbol to trade, a strategy to apply, and an exchange to trade on.
 */
public class Trader implements Runnable {
    private final static int MAX_RECONNECT_TRIES = 5;
    private final Exchange exchange;
    private final Period period;
    private final Strategy strategy;
    private final Symbol symbol;
    private double lastBuyingPrice;

    /**
     * Creates an instance of a trading bot
     * @param exchange the exchange to trade on
     * @param period period used for computation (e.g. 5 minutes)
     * @param symbol crypto pair to trade (e.g. BTCUSDT)
     * @param strategy the user's custom strategy
     */
    public Trader(Exchange exchange, Period period, Symbol symbol, Strategy strategy) {
        this.exchange = exchange;
        this.period = period;
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
    public void run()  {
        while (!Thread.interrupted()) {
            Try.toRunTimes(this::tick, MAX_RECONNECT_TRIES);
            try {
                Thread.sleep(this.period.toMillis() / 120);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     *  Calculates one iteration of the strategy.
     *  It fetches the available balances on the exchange, the last
     *  ticker price and enough price recors to compute the selected strategy.
     */
    private void tick() {
        HashMap<String, Double> balances = exchange.getBaseAndQuoteBalances(symbol);
        double tickerPrice = exchange.getTicker(symbol);

        int recordsToFetchEntrance = strategy.getEntranceRule().getNbOfRecordsToFetch();
        int recordsToFetchExit = strategy.getExitRule().getNbOfRecordsToFetch();
        int maxRecords = Math.max(recordsToFetchEntrance, recordsToFetchExit);

        double[] closePrices = exchange.getClosePrices(symbol, period, maxRecords);

        StrategyResult result = strategy.execute(tickerPrice, closePrices);

        System.out.println(
                "Base balance: " + balances.get("base") +
                "\nQuote balance: " + balances.get("quote") +
                "\nTicker: " + tickerPrice +
                "\nStrategy: " + strategy);

        if (result == StrategyResult.BUY && balances.get("quote") > symbol.MIN_QUOTE_TRANSACTION) {
            exchange.buy(symbol, balances.get("quote"));
            lastBuyingPrice = tickerPrice;
        } else if ((result == StrategyResult.SELL || tickerPrice < lastBuyingPrice * 0.99)
                && balances.get("base") > symbol.MIN_BASE_TRANSACTION) {
            exchange.sell(symbol, balances.get("base"));
            lastBuyingPrice = 0.;
        }
    }

    @Override
    public String toString() {
        return String.format("On: %s\nSymbol:  %s\nStrategy: %s\n", exchange, symbol, strategy);
    }
}
