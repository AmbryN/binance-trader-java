package com.binance.trader.intefaces;

import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.trader.classes.data.Balance;
import com.binance.trader.enums.Crypto;
import com.binance.trader.enums.Period;
import com.binance.trader.enums.StrategyResult;
import com.binance.trader.enums.Symbol;

import java.util.HashMap;

public interface Strategy {
    StrategyResult execute(Symbol symbol, HashMap<String, Double> balances, double tickerPrice);

    String describe();

    String toString();
}
