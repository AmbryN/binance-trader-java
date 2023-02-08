package org.crypto.bot.classes.indicators;

/**
 * Interface representing a technical analysis indicator
 */
public interface Indicator {
    /**
     * Gets the last value of the indicator
     * @param prices prices to calculate the indicator on
     * @return the last value of the indicator
     */
    double getLastValue(double[] prices);

    /**
     * Gets all the values computed for the indicator
     * @param prices prices to calculate the indicator on
     * @return the array of all the indicator's values
     */
    double[] getAllValues(double[] prices);

    /**
     * Gets the minimal amount of records needed for the
     * indicator to be computed.
     * @return the minimal amount of records needed
     */
    int getNbOfRecordsToFetch();
}
