package org.crypto.bot.classes.rules;

import org.crypto.bot.classes.indicators.Indicator;

/**
 * A trading rule satisfied when the first
 * indicator is over the second indicator.
 */
public class OverIndicatorRule implements Rule {

    Indicator first;
    Indicator second;

    public OverIndicatorRule(Indicator first, Indicator second) {
       this.first = first;
       this.second = second;
    }

    @Override
    public boolean isSatisfied(double ticker, double[] prices) {
        return first.getLastValue(prices) > second.getLastValue(prices);
    }

    @Override
    public int getNbOfRecordsToFetch() {
        return Math.max(first.getNbOfRecordsToFetch(), second.getNbOfRecordsToFetch());
    }

    @Override
    public Rule and(Rule rule) {
        return new AndRule(this, rule);
    }

    @Override
    public Rule or(Rule rule) {
        return new OrRule(this, rule);
    }

    @Override
    public String toString() {
        return "(" + first + " OVER " + second + ")";
    }
}
