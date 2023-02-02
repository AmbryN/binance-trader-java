package org.crypto.bot.classes.strategies;

import org.crypto.bot.classes.selectors.DoubleSelector;
import org.crypto.bot.enums.StrategyResult;
import org.crypto.bot.enums.Symbol;
import org.crypto.bot.interfaces.Exchange;
import org.crypto.bot.interfaces.Strategy;

import java.util.HashMap;

public class MACDr1Strategy extends MACDStrategy implements Strategy {

    protected double minSpread;
    protected boolean isOverSpread;
    protected boolean isUnderSpread;

//    this.minSpread = new DoubleSelector().startSelector("Min Spread before Buy occurs (as double: e.g. 2.5 for 0.025): ");
    public MACDr1Strategy() {
        super();
    }

    public void setMinSpread(double spread) { this.minSpread = spread; }

    @Override
    protected StrategyResult buyDecision(double tickerPrice, double[] closePrices) {
        computeParams(closePrices, tickerPrice);
        if (isOverSpread) {
            return StrategyResult.BUY;
        } else if (isUnderSpread) {
            return StrategyResult.SELL;
        }
        return StrategyResult.HOLD;
    }

    protected void computeParams(double[] closePrices, double tickerPrice) {
        getMacdAndSignalLines(closePrices);
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

    public String getCurrentStatus() {
        return super.getCurrentStatus() +
                "\nOver Spread " + isOverSpread +
                "\nUnder Spread " + isUnderSpread;

    }

    @Override
    public String describe() {
        return super.describe() +
                "\n-> Min Spread before Buy: " + this.minSpread;
    }

    @Override
    public String toString() {
        return super.toString() +
                " - Refined 1";
    }
}
