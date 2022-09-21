package com.binance.trader.intefaces;

import com.binance.trader.enums.Symbol;

public interface Strategy {
    public void execute(Symbol symbol);
}
