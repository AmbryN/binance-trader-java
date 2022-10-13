package com.binance.trader.intefaces;

import com.binance.trader.enums.StrategyResult;
import com.binance.trader.enums.Symbol;

import java.util.HashMap;

/**
 * Trading strategy used by the bot
 */
public interface Strategy {
    StrategyResult execute(Symbol symbol, HashMap<String, Double> balances, double tickerPrice);

    void init(Exchange exchange);

    String describe();

    String toString();
}
