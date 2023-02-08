package org.crypto.bot.classes.rules;

/**
 * Interface representing a trading Rule
 */
public interface Rule {
    /**
     * Indicates if the rule is satisfied given the entry parameters
     * @param ticker the current price
     * @param prices the prices used to compute underlying indicators
     * @return if the rule is satisfied or not
     */
    boolean isSatisfied(double ticker, double[] prices);

    /**
     * Gets the amount of records needed to compute the underlying indicators of the rule.
     * @return the amount of records to fetch to verify the rule
     */
    int getNbOfRecordsToFetch();

    /**
     * Creates a new AndRule between this rule and the provided one.
     * @param rule the rule with which to create a new AndRule
     * @return the AndRule
     */
    Rule and(Rule rule);

    /**
     * Creates a new OrRule between this rule and the provided one.
     * @param rule the rule with which to create a new OrRule
     * @return the OrRule
     */
    Rule or(Rule rule);
}
