package com.binance.trader.classes.strategies;

import com.binance.trader.enums.CrossingDirection;
import com.binance.trader.enums.StrategyResult;
import com.binance.trader.enums.Symbol;
import com.binance.trader.interfaces.Strategy;

import java.util.HashMap;

public class MACDr2Strategy extends MACDStrategy implements Strategy {

    private Double lastBuyingPrice;

    public MACDr2Strategy() {
        super();
        lastBuyingPrice = null;
    }

    protected void setLastBuyingPrice(Double lastBuyingPrice) {
        this.lastBuyingPrice = lastBuyingPrice;
    }

    @Override
    protected StrategyResult buyDecision(Symbol symbol, double tickerPrice) {
        computeParams(symbol);
        if (getCurrentMACD() < 0 && crossingDirection == CrossingDirection.UP) {
            lastBuyingPrice = tickerPrice;
            return StrategyResult.BUY;
        } else if (crossingDirection == CrossingDirection.DOWN || (lastBuyingPrice != null && tickerPrice < lastBuyingPrice)) { // TODO : Write tests for "lastBuyingPrice"
            lastBuyingPrice = null;
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
