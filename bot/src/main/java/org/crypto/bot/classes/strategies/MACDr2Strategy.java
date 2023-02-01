package org.crypto.bot.classes.strategies;

import org.crypto.bot.enums.StrategyResult;
import org.crypto.bot.enums.Symbol;
import org.crypto.bot.interfaces.Strategy;

public class MACDr2Strategy extends MACDStrategy implements Strategy {

    public MACDr2Strategy() {
        super();
    }

    @Override
    protected StrategyResult buyDecision(double tickerPrice, double[] closePrices) {
        computeParams(closePrices);
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
