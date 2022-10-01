package com.binance.trader.intefaces;

import com.binance.trader.enums.Period;
import com.binance.trader.enums.Symbol;

public interface Strategy {
    void init();
    void execute(Symbol symbol);
    Period getPeriod();
    int getNbOfPeriods();
}
