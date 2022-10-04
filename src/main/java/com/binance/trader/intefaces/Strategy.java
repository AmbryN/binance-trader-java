package com.binance.trader.intefaces;

import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.trader.enums.Period;
import com.binance.trader.enums.Symbol;

public interface Strategy {
    void init(SpotClientImpl client);

    void execute(Symbol symbol);

    String describe();

    String toString();
}
