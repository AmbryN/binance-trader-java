package org.crypto.bot.classes.indicators;

import org.crypto.bot.enums.Period;
import org.crypto.bot.utils.Calculus;

import java.util.ArrayList;

public class MACDIndicator implements Indicator {
    private int shortNbOfPeriods;
    private int longNbOfPeriods;
    private int signalNbOfPeriods;
    private double lastValue;

    public MACDIndicator() {}

    public MACDIndicator(int shortNbOfPeriods, int longNbOfPeriods, int signalNbOfPeriods) {
        this.shortNbOfPeriods = shortNbOfPeriods;
        this.longNbOfPeriods = longNbOfPeriods;
        this.signalNbOfPeriods = signalNbOfPeriods;
    }

    public void setShortNbOfPeriods(int shortNbOfPeriods) {
        this.shortNbOfPeriods = shortNbOfPeriods;
    }

    public void setLongNbOfPeriods(int longNbOfPeriods) {
        this.longNbOfPeriods = longNbOfPeriods;
    }

    public void setSignalNbOfPeriods(int signalNbOfPeriods) {
        this.signalNbOfPeriods = signalNbOfPeriods;
    }

    @Override
    public double getValue(double[] closePrices) {
        // Compute the short EMA (generally 12) and the long EMA (generally 26) used for the MACD line
        double[] shortEMAS = Calculus.expMovingAvgesWithSize(closePrices, this.shortNbOfPeriods);
        double[] longEMAs = Calculus.expMovingAvgesWithSize(closePrices, this.longNbOfPeriods);

        // Compute the MACD Line which is the subtraction of the longEMA from the shortEMA
        double[] MACDLine = this.computeMACDLine(shortEMAS, longEMAs);

        // Compute the signal line which is the EMA9 of the MACD line (subtractions)
        double[] signalLine = Calculus.expMovingAvgesWithSize(MACDLine, signalNbOfPeriods);
        this.lastValue = MACDLine[MACDLine.length - 1] - signalLine[signalLine.length - 1];
        return this.lastValue;
    }

    protected double[] computeMACDLine(double[] shortEMAs, double[] longEMAs) {
        ArrayList<Double> MACDLine = new ArrayList<>();
        // Binance calculates the Signal with at least { 6 * signalNbOfPeriods }
        int recordsNeededForSignal = this.signalNbOfPeriods * 6 - 5;
        int lastIndexShortEMA = shortEMAs.length - 1;
        int lastIndexLongEMA = longEMAs.length - 1;
        for (int i=1; i <= recordsNeededForSignal; i++) {
            MACDLine.add(shortEMAs[lastIndexShortEMA - recordsNeededForSignal + i] - longEMAs[lastIndexLongEMA - recordsNeededForSignal + i]);
        }
        return MACDLine.stream().mapToDouble(Double::doubleValue).toArray();
    }

    @Override
    public int getNbOfRecordsToFetch() {
        // To compute the {size} EMA, you normally need {size * 2 - 1} records,
        // but binance uses at least { 5 * size } to be more accurate.
        return this.longNbOfPeriods * 5 - 4;
    }

    @Override
    public String toString() {
        return  "(MACD: Short " + this.shortNbOfPeriods + " / Long " + this.longNbOfPeriods + " / Signal " + this.signalNbOfPeriods + " - Current: " + lastValue + ")";
    }
}
