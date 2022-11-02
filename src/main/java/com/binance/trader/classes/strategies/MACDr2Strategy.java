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
        lastBuyingPrice = 0.;
    }

    protected void setLastBuyingPrice(Double lastBuyingPrice) {
        this.lastBuyingPrice = lastBuyingPrice;
    }

    @Override
    protected StrategyResult buyDecision(Symbol symbol, double tickerPrice) {
        computeParams(symbol);
        if (getCurrentMACD() < 0 && getCurrentMACD() > getCurrentSignal()) {
            if (lastBuyingPrice == 0.) {
                setLastBuyingPrice(tickerPrice);
            }
            return StrategyResult.BUY;
        } else if (getCurrentMACD() < getCurrentSignal() || (lastBuyingPrice != null && tickerPrice < lastBuyingPrice)) {
            setLastBuyingPrice(0.);
            return StrategyResult.SELL;
        }
        return StrategyResult.HOLD;
    }

    @Override
    protected String currentStatus(HashMap<String, Double> balances, double tickerPrice) {
        return super.currentStatus(balances, tickerPrice) +
                "\nLast buying Price " + lastBuyingPrice;

    }

    @Override
    public String toString() {
        return super.toString() +
                " - Refined 2";
    }
}
