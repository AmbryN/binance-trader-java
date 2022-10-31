package com.binance.trader.classes.strategies;

import com.binance.trader.enums.CrossingDirection;
import com.binance.trader.enums.StrategyResult;
import com.binance.trader.enums.Symbol;
import com.binance.trader.interfaces.Strategy;

import java.util.HashMap;

public class MACDr2Strategy extends MACDStrategy implements Strategy {

    private Double lastBuyingPrice;
    private boolean macdUnderZeroWhenCrossed;

    public MACDr2Strategy() {
        super();
        lastBuyingPrice = null;
        macdUnderZeroWhenCrossed = false;
    }

    protected void setLastBuyingPrice(Double lastBuyingPrice) {
        this.lastBuyingPrice = lastBuyingPrice;
    }

    @Override
    protected StrategyResult buyDecision(Symbol symbol, double tickerPrice) {
        computeParams(symbol);
        if (getCurrentMACD() < 0 && crossingDirection == CrossingDirection.UP) {
            macdUnderZeroWhenCrossed = true;
            if (lastBuyingPrice == null) {
                lastBuyingPrice = tickerPrice;
            }
            return StrategyResult.BUY;
        } else if (crossingDirection == CrossingDirection.DOWN || (lastBuyingPrice != null && tickerPrice < lastBuyingPrice)) {
            macdUnderZeroWhenCrossed = false;
            lastBuyingPrice = null;
            return StrategyResult.SELL;
        }
        return StrategyResult.HOLD;
    }

    @Override
    protected String currentStatus(HashMap<String, Double> balances, double tickerPrice) {
        return super.currentStatus(balances, tickerPrice) +
                "\nMACD was under Zero: " + macdUnderZeroWhenCrossed +
                "\nLast buying Price " + lastBuyingPrice;

    }

    @Override
    public String toString() {
        return super.toString() +
                " - Refined 2";
    }
}
