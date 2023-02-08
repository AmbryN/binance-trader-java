package org.crypto.bot.classes.indicators;

public interface Indicator {
    double getValue(double[] prices);
    double[] getAllValues(double[] prices);
    int getNbOfRecordsToFetch();
}
