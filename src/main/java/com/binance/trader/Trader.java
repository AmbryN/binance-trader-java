package com.binance.trader;

import com.binance.trader.intefaces.Strategy;
import com.binance.trader.enums.Symbol;

public class Trader {

    private Symbol symbol;
    private Strategy strategy;
    
        
    public Trader(Symbol symbol, Strategy stragegy) {
        this.symbol = symbol;
        this.strategy = stragegy;
    }

    public void trade() {
        this.strategy.execute(this.symbol);
    }    
}
