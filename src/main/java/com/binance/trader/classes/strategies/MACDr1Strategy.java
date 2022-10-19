package com.binance.trader.classes.strategies;

import com.binance.trader.classes.selectors.DoubleSelector;
import com.binance.trader.enums.StrategyResult;
import com.binance.trader.enums.Symbol;
import com.binance.trader.interfaces.Exchange;
import com.binance.trader.interfaces.Strategy;

import java.util.ArrayList;
import java.util.HashMap;

public class MACDr1Strategy extends MACDStrategy implements Strategy {

    protected double minSpread;

    public MACDr1Strategy() {
        super();
    }

    public void init(Exchange exchange) {
        super.init(exchange);
        this.minSpread = new DoubleSelector().startSelector("Min Spread before Buy occurs (as double: e.g. 2.5 for 0.025): ");
    }

    @Override
    public StrategyResult execute(Symbol symbol, HashMap<String, Double> balances, double tickerPrice) {
        HashMap<String, ArrayList<Double>> lines = this.getMacdAndSignalLines(symbol);
        ArrayList<Double> MACDLine = lines.get("macd");
        ArrayList<Double> signalLine = lines.get("signal");
        double newMACD = MACDLine.get(MACDLine.size() - 1);
        double newSignal = signalLine.get(signalLine.size() - 1);

        boolean consistentUpCross = this.isConsistentUpCross(newSignal, newMACD, tickerPrice);
        boolean downCross = this.isDownCross(newSignal, newMACD, tickerPrice);
        System.out.println("Base balance: " + balances.get("base") +
                "\nQuote balance: " + balances.get("quote") +
                "\nTicker " + tickerPrice +
                "\nConsistent UP " + consistentUpCross +
                "\nDown " + downCross +
                "\nSignal " + newSignal +
                "\nMACD " + newMACD);

        if (consistentUpCross && balances.get("quote") > symbol.MIN_QUOTE_TRANSACTION) {
            return StrategyResult.BUY;
        } else if (downCross && balances.get("base") > symbol.MIN_BASE_TRANSACTION) {
            return StrategyResult.SELL;
        }
        return StrategyResult.NONE;
    }

    private boolean isConsistentUpCross(double newSignal, double newMACD, double ticker) {
        return newMACD > newSignal && (Math.abs(newMACD - newSignal) / ticker) * 100 > minSpread / 100.;
    }
    private boolean isDownCross(double newSignal, double newMACD, double ticker) {
        return (newMACD > newSignal && (Math.abs(newMACD - newSignal) / ticker) * 100 < (minSpread / 100.) * 0.8) || newMACD < newSignal;
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
