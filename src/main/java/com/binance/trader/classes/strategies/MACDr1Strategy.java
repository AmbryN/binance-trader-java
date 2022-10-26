package com.binance.trader.classes.strategies;

import com.binance.trader.classes.selectors.DoubleSelector;
import com.binance.trader.enums.CrossingDirection;
import com.binance.trader.enums.StrategyResult;
import com.binance.trader.enums.Symbol;
import com.binance.trader.interfaces.Exchange;
import com.binance.trader.interfaces.Strategy;

import java.util.HashMap;

public class MACDr1Strategy extends MACDStrategy implements Strategy {

    protected double minSpread;
    protected boolean isOverSpread;
    protected boolean isUnderSpread;

    public MACDr1Strategy() {
        super();
    }

    protected void setMinSpread(double spread) { this.minSpread = spread; }

    @Override
    public void init(Exchange exchange) {
        super.init(exchange);
        this.minSpread = new DoubleSelector().startSelector("Min Spread before Buy occurs (as double: e.g. 2.5 for 0.025): ");
    }

    @Override
    protected StrategyResult buyDecision(Symbol symbol, double tickerPrice) {
        computeParams(symbol, tickerPrice);
        if (isOverSpread) {
            return StrategyResult.BUY;
        } else if (isUnderSpread) {
            return StrategyResult.SELL;
        }
        return StrategyResult.HOLD;
    }

    protected void computeParams(Symbol symbol, double tickerPrice) {
        getMacdAndSignalLines(symbol);
        computeCrossingDirection();
        isOverSpread(tickerPrice);
        isUnderSpread(tickerPrice);
    }

    protected void isOverSpread(double tickerPrice) {
        double currentMACD = getCurrentMACD();
        double currentSignal = getCurrentSignal();
        this.isOverSpread = ((currentMACD - currentSignal) / tickerPrice) * 100 > minSpread / 100.;
    }

    protected void isUnderSpread(double tickerPrice) {
        double currentMACD = getCurrentMACD();
        double currentSignal = getCurrentSignal();
        this.isUnderSpread = ((currentMACD - currentSignal) / tickerPrice) * 100 < (minSpread / 100.) * 0.8;
    }

    @Override
    protected String currentStatus(HashMap<String, Double> balances, double tickerPrice) {
        return super.currentStatus(balances, tickerPrice) +
                "\nOver Spread " + isOverSpread +
                "\nUnder Spread " + isUnderSpread;

    }

    @Override
    public String describe() {
        return super.describe() +
                "\nMin Spread before Buy: " + this.minSpread;
    }

    @Override
    public String toString() {
        return super.toString() +
                " - Refined 1";
    }
}
