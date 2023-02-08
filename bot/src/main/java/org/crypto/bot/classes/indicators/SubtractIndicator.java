package org.crypto.bot.classes.indicators;

import org.crypto.bot.utils.Calculus;

import java.util.ArrayList;

public class SubtractIndicator implements Indicator {

    private Indicator first;
    private Indicator second;

    public SubtractIndicator() {}

    public SubtractIndicator(Indicator first, Indicator second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public double getValue(double[] closePrices) {
       return this.first.getValue(closePrices) - this.second.getValue(closePrices);
    }

    @Override
    public double[] getAllValues(double[] prices) {
        double[] firstValues = this.first.getAllValues(prices);
        double[] secondValues = this.second.getAllValues(prices);
        double[] result = new double[Math.min(firstValues.length, secondValues.length)];

        for (int i = result.length - 1; i >= 0; i--) {
            result[i] = firstValues[i] - secondValues[i];
        }
        return result;
    }

    @Override
    public int getNbOfRecordsToFetch() {
        return Math.max(first.getNbOfRecordsToFetch(), second.getNbOfRecordsToFetch());
    }

    @Override
    public String toString() {
        return  "(" + first + " MINUS " + second + "";
    }
}
