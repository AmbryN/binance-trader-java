package org.crypto.bot.interfaces;

import org.crypto.bot.enums.Period;
import org.crypto.bot.enums.Symbol;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

/**
 * Defines the method an Exchange facade needs to implement to be used by the client application.
 */
public interface Exchange {
    CompletableFuture<HashMap<String, Double>> getBaseAndQuoteBalances(Symbol symbol);
    CompletableFuture<Double> getTicker(Symbol symbol);

    CompletableFuture<double[]> getClosePrices(Symbol symbol, Period period, int nbOfRecordsToFetch);

    void buy(Symbol symbol, double tickerPrice, double quoteBalance);
    void sell(Symbol symbol, double tickerPrice, double baseBalance);
}
