package org.crypto.bot.classes.rules;

public interface Rule {
    boolean isSatisfied(double ticker, double[] prices);
    int getNbOfRecordsToFetch();
    Rule and(Rule rule);
    Rule or(Rule rule);
}
