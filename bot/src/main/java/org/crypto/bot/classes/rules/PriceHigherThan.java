package org.crypto.bot.classes.rules;

public class PriceHigherThan implements Rule {
    private final double value;
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
    public String toString() {
        return "( Higher Than: " + value + " )";
    }
}
