package com.binance.trader.classes.strategies;

import com.binance.trader.enums.CrossingDirection;
import com.binance.trader.enums.StrategyResult;
import com.binance.trader.enums.Symbol;
import com.binance.trader.interfaces.Strategy;

import java.util.HashMap;

public class MACDr2Strategy extends MACDr1Strategy implements Strategy {

    public MACDr2Strategy() {
        super();
    }

    @Override
    protected StrategyResult buyDecision(Symbol symbol, HashMap<String, Double> balances, double tickerPrice) {
        computeParams(symbol, balances, tickerPrice);
        printCurrentStatus(balances, tickerPrice);
        if (getCurrentMACD() < 0 && crossingDirection == CrossingDirection.UP && isOverSpread) {
            return StrategyResult.BUY;
        } else if (crossingDirection == CrossingDirection.DOWN && isUnderSpread) {
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
