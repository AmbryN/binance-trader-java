package org.crypto.bot.classes.indicators;

public class PriceIndicator implements Indicator {
    private final int size;

    public PriceIndicator() {
        this.size = 0;
    }

    public PriceIndicator(double[] prices) {
        this.size = prices.length;
    }

    public int getSize() {
        return size;
    }

    @Override
    public double getLastValue(double[] prices) {
        return prices[size - 1];
    }

    @Override
    public double[] getAllValues(double[] prices) {
        return prices;
    }

    @Override
    public int getNbOfRecordsToFetch() {
        return this.size;
    }
}
