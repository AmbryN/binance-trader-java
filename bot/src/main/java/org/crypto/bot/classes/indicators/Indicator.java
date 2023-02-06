package org.crypto.bot.classes.indicators;

public interface Indicator {
    double getValue(double[] prices);

    int getNbOfRecordsToFetch();
    String describe();
}
