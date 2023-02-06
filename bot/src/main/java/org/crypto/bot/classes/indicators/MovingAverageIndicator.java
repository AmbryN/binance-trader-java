package org.crypto.bot.classes.indicators;

import org.crypto.bot.enums.Period;

public abstract class MovingAverageIndicator implements Indicator {

    protected Period period;
    protected int nbOfPeriods;

    protected MovingAverageIndicator() {}

    public void setPeriod(Period period) {
        this.period = period;
    }
    public Period getPeriod() {
        return this.period;
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
        return "\n-> Time Period: " + this.period +
                "\n-> Number of Periods: " + this.nbOfPeriods;
    }
}
