package org.crypto.bot.classes.rules;

import org.crypto.bot.classes.indicators.Indicator;

public class UnderIndicatorRule implements Rule {
    Indicator first;
    Indicator second;

    public UnderIndicatorRule() {}

    public UnderIndicatorRule(Indicator first, Indicator second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean isSatisfied(double ticker, double[] prices) {
        return first.getValue(prices) < second.getValue(prices);
    }

    @Override
    public int getNbOfRecordsToFetch() {
        return Math.max(first.getNbOfRecordsToFetch(), second.getNbOfRecordsToFetch());
    }

    @Override
    public String toString() {
        return "(" + first + " UNDER " + second + ")";
    }
}
