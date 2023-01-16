package com.binance.trader.interfaces;

import com.binance.trader.enums.Symbol;

import java.util.HashMap;

/**
 * Defines the method an Exchange facade needs to implement to be used by the client application.
 */
public interface Exchange {
    HashMap<String, Double> getBaseAndQuoteBalances(Symbol symbol);
    Double getTicker(Symbol symbol);

    Double[] getClosePrices(Symbol symbol, String period, int nbOfRecordsToFetch);

    void buy(Symbol symbol, double tickerPrice, double quoteBalance);
    void sell(Symbol symbol, double tickerPrice, double baseBalance);
}
