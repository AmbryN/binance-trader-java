package com.binance.trader.classes.strategies;

import com.binance.trader.enums.StrategyResult;
import com.binance.trader.enums.Symbol;
import com.binance.trader.interfaces.Strategy;

public class MACDr2Strategy extends MACDStrategy implements Strategy {

    public MACDr2Strategy() {
        super();
    }

    @Override
    protected StrategyResult buyDecision(Symbol symbol, double tickerPrice) {
        computeParams(symbol);
        if (getCurrentMACD() < 0 && getCurrentMACD() > getCurrentSignal()) {
            return StrategyResult.BUY;
        } else if (getCurrentMACD() < getCurrentSignal()) {
            return StrategyResult.SELL;
        }
        return StrategyResult.HOLD;
    }

    @Override
    public String toString() {
        return super.toString() +
                " - Refined 2";
    }
}
