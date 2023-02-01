package org.crypto.bot.interfaces;

import org.crypto.bot.enums.Period;
import org.crypto.bot.enums.StrategyResult;
import org.crypto.bot.enums.Symbol;

import java.util.HashMap;

/**
 * Trading strategy used by the bot
 */
public interface Strategy {
    StrategyResult execute(double tickerPrice, double[] closePrices);

    void init();

    int getAmountOfRecordsToFetch();

    String getCurrentStatus();

    Period getPeriod();

    String describe();
}
