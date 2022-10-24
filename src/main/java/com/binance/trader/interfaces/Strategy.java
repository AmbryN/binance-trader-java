package com.binance.trader.interfaces;

import com.binance.trader.enums.StrategyResult;
import com.binance.trader.enums.Symbol;

import java.util.HashMap;

/**
 * Trading strategy used by the bot
 */
public interface Strategy {
    StrategyResult execute(Symbol symbol, double tickerPrice);

    void init(Exchange exchange);

    void printCurrentStatus(HashMap<String, Double> balances, double tickerPrice);

    String describe();

    String toString();
}
