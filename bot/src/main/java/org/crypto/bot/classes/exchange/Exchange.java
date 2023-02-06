package org.crypto.bot.classes.exchange;

import org.crypto.bot.enums.Period;
import org.crypto.bot.enums.Symbol;

import java.util.HashMap;

/**
 * Defines the method an Exchange facade needs to implement to be used by the client application.
 */
public interface Exchange {
    HashMap<String, Double> getBaseAndQuoteBalances(Symbol symbol);
    double getTicker(Symbol symbol);

    double[] getClosePrices(Symbol symbol, Period period, int nbOfRecordsToFetch);

    void buy(Symbol symbol, double quoteBalance);
    void sell(Symbol symbol, double baseBalance);
}
