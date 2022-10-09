package com.binance.trader.classes.strategies;

import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.trader.classes.selectors.DoubleSelector;
import com.binance.trader.enums.OrderSide;
import com.binance.trader.enums.Symbol;
import com.binance.trader.intefaces.Strategy;

import java.util.ArrayList;
import java.util.HashMap;

public class MACDr1Strategy extends MACDStrategy implements Strategy {

    private double minSpread = -1.0;

    @Override
    public void init(SpotClientImpl client) {
        super.init(client);
        while (this.minSpread < 0) {
            DoubleSelector selector = new DoubleSelector();
            this.minSpread = selector.startSelector("Min Spread before Buy occurs (as int: e.g. 5 for 0.05): ");
        }
    }
    @Override
    public void execute(Symbol symbol) {
        while(true) {
            HashMap<String, Double> balances = this.getBalances(symbol);
            double tickerPrice = getTickerPrice(symbol);

            HashMap<String, ArrayList<Double>> lines = this.getLines(symbol);
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
                this.generateOrder(symbol, OrderSide.BUY, balances, tickerPrice);
            } else if (downCross && balances.get("base") > symbol.MIN_BASE_TRANSACTION) {
                this.generateOrder(symbol, OrderSide.SELL, balances, tickerPrice);
            }
        }
    }

    protected boolean isConsistentUpCross(double newSignal, double newMACD, double ticker) {
        return newMACD > newSignal && (Math.abs(newMACD - newSignal) / ticker) * 100 > minSpread / 100.;
    }
    protected boolean isDownCross(double newSignal, double newMACD, double ticker) {
        return (newMACD > newSignal && (Math.abs(newMACD - newSignal) / ticker) * 100 < (minSpread / 100.) / 2) || newMACD < newSignal;
    }

    @Override
    public String describe() {
        return "Time Period: " + this.period +
                "\nShort Number of Periods: " + this.shortNbOfPeriods +
                "\nLong Number of Periods: " + this.longNbOfPeriods +
                "\nSignal Number of Periods: " + this.signalNbOfPeriods +
                "\nMin Spread before Buy: " + this.minSpread;
    }

    @Override
    public String toString() {
        return "Moving Average Convergence Divergence - Refined 1";
    }
}
