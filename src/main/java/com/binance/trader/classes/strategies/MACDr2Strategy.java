package com.binance.trader.classes.strategies;

import com.binance.trader.enums.StrategyResult;
import com.binance.trader.enums.Symbol;
import com.binance.trader.interfaces.Strategy;

import java.util.HashMap;

import static com.binance.trader.enums.CrossingDirection.DOWN;
import static com.binance.trader.enums.CrossingDirection.UP;

public class MACDr2Strategy extends MACDStrategy implements Strategy {

    public MACDr2Strategy() {
        super();
    }

    @Override
    protected StrategyResult buyDecision(Symbol symbol, double tickerPrice) {
        computeParams(symbol);
        if (getCurrentMACD() < 0 && crossingDirection == UP) {
            return StrategyResult.BUY;
        } else if (crossingDirection == DOWN) {
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
