package org.crypto.bot.classes.rules;

import org.crypto.bot.classes.indicators.Indicator;

public class OverIndicatorRule implements Rule {

    Indicator first;
    Indicator second;

    public OverIndicatorRule() { }

    public OverIndicatorRule(Indicator first, Indicator second) {
       this.first = first;
       this.second = second;
    }

    @Override
    public boolean isSatisfied(double ticker, double[] prices) {
        return first.getValue(prices) > second.getValue(prices);
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
