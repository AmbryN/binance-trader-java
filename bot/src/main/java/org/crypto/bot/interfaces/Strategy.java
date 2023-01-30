package org.crypto.bot.interfaces;

import org.crypto.bot.enums.Period;
import org.crypto.bot.enums.StrategyResult;
import org.crypto.bot.enums.Symbol;

import java.util.HashMap;

/**
 * Trading strategy used by the bot
 */
public interface Strategy {
    StrategyResult execute(Symbol symbol, double tickerPrice);

    void init(Exchange exchange);

    void printCurrentStatus(HashMap<String, Double> balances, double tickerPrice);

    Period getPeriod();

    String describe();

    String toString();
}
