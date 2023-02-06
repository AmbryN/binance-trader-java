package org.crypto.bot.classes.indicators;

public abstract class MAIndicator implements Indicator {

    protected int nbOfPeriods;

    protected MAIndicator() {}

    protected MAIndicator(int nbOfPeriods) {
        this.nbOfPeriods = nbOfPeriods;
    }

    public void setNbOfPeriods(int nbOfPeriods) {
        this.nbOfPeriods = nbOfPeriods;
    }

    public int getNbOfRecordsToFetch() {
        return this.nbOfPeriods;
    }

    public double getValue(double[] closePrices) {
        return calculateMovingAvg(closePrices);
    }

    protected abstract double calculateMovingAvg(double[] closePrices);

    public String describe() {
        return "\n-> Number of Periods: " + this.nbOfPeriods;
    }
}
