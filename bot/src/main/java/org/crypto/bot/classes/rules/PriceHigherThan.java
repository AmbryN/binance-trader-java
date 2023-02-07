package org.crypto.bot.classes.rules;

public class PriceHigherThan implements Rule {
    private double value;

    public PriceHigherThan(double value) {
        this.value = value;
    }


    @Override
    public boolean isSatisfied(double ticker, double[] prices) {
        return ticker > value;
    }

    @Override
    public int getNbOfRecordsToFetch() {
        return 0;
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
        return "( Higher Than: " + value + " )";
    }
}
