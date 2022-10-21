package com.binance.trader.classes.strategies;

import com.binance.trader.classes.selectors.DoubleSelector;
import com.binance.trader.enums.StrategyResult;
import com.binance.trader.enums.Symbol;
import com.binance.trader.interfaces.Exchange;
import com.binance.trader.interfaces.Strategy;

import java.util.ArrayList;
import java.util.HashMap;

public class MACDr2Strategy extends MACDr1Strategy implements Strategy {

    public MACDr2Strategy() {
        super();
    }
    private boolean isConsistentUpCross(double newSignal, double newMACD, double ticker) {
        return newMACD < 0
                && newMACD > newSignal
                && (Math.abs(newMACD - newSignal) / ticker) * 100 > this.minSpread / 100.;
    }

    @Override
    public String toString() {
        return super.toString() +
                " - Refined 2";
    }
}
