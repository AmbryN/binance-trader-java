package org.crypto.bot.classes.rules;

public class OrRule implements Rule {
    private final Rule firstRule;
    private final Rule secondRule;

    public OrRule(Rule firstRule, Rule secondRule) {
        this.firstRule = firstRule;
        this.secondRule = secondRule;
    }

    @Override
    public boolean isSatisfied(double ticker, double[] prices) {
        return firstRule.isSatisfied(ticker, prices) || secondRule.isSatisfied(ticker, prices);
    }

    @Override
    public int getNbOfRecordsToFetch() {
        return Math.max(firstRule.getNbOfRecordsToFetch(), secondRule.getNbOfRecordsToFetch());
    }

    @Override
    public String toString() {
        return "(" + firstRule + " OR " + secondRule + ")";
    }
}