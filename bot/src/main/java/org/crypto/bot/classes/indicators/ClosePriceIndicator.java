package org.crypto.bot.classes.indicators;

public class ClosePriceIndicator implements Indicator {
    @Override
    public double getLastValue(double[] prices) {
        return prices[prices.length - 1];
    }

    @Override
    public double[] getAllValues(double[] prices) {
        return prices;
    }

    @Override
    public int getNbOfRecordsToFetch() {
        return 0;
    }
}
