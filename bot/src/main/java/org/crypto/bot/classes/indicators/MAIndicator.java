package org.crypto.bot.classes.indicators;

public abstract class MAIndicator implements Indicator {
    protected double lastValue;

    protected Indicator indicator;
    protected int nbOfPeriods;

    protected MAIndicator() {}

    protected MAIndicator(Indicator indicator, int nbOfPeriods) {
        this.indicator = indicator;
        this.nbOfPeriods = nbOfPeriods;
    }

    public void setNbOfPeriods(int nbOfPeriods) {
        this.nbOfPeriods = nbOfPeriods;
    }

    public int getNbOfRecordsToFetch() {
        return this.nbOfPeriods;
    }

    @Override
    public double getLastValue(double[] closePrices) {
        double[] values = this.indicator.getAllValues(closePrices);
        return calculateMovingAvg(values);
    }

    public double[] getAllValues(double[] closePrices) {
        double[] values = this.indicator.getAllValues(closePrices);
        return new double[] { calculateMovingAvg(values)};
    }

    protected abstract double calculateMovingAvg(double[] closePrices);
}
