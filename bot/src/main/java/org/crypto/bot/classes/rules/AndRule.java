package org.crypto.bot.classes.rules;

/**
 * A rule satisfied when both underlying rules are satisfied (Logical AND).
 */
public class AndRule implements Rule {

    private final Rule firstRule;
    private final Rule secondRule;

    public AndRule(Rule firstRule, Rule secondRule) {
        this.firstRule = firstRule;
        this.secondRule = secondRule;
    }

    @Override
    public boolean isSatisfied(double ticker, double[] prices) {
        return firstRule.isSatisfied(ticker, prices) && secondRule.isSatisfied(ticker, prices);
    }

    @Override
    public int getNbOfRecordsToFetch() {
        return Math.max(firstRule.getNbOfRecordsToFetch(), secondRule.getNbOfRecordsToFetch());
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
        return "(" + firstRule + " AND " + secondRule + ")";
    }
}
