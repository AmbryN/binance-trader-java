package com.binance.trader.interfaces;

import com.binance.trader.enums.Symbol;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Defines the method an Exchange facade needs to implement in order
 * to be used by the client application
 */
public interface Exchange {
    HashMap<String, Double> getBaseAndQuoteBalances(Symbol symbol);
    Double getTickerPrice(Symbol symbol);

    Double[] getClosePrices(Symbol symbol, String period, int nbOfRecordsToFetch);

    void buy(Symbol symbol, double tickerPrice, double quoteBalance);
    void sell(Symbol symbol, double tickerPrice, double baseBalance);
}
