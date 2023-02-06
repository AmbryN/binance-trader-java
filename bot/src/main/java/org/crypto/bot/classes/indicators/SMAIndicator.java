package org.crypto.bot.classes.indicators;

import org.crypto.bot.utils.Calculus;

public class SMAIndicator extends MovingAverageIndicator {

    public SMAIndicator() {}

    @Override
    protected double calculateMovingAvg(double[] closePrices) {
        return Calculus.simpleMovingAvg(closePrices);
    }

    @Override
    public String toString() {
        return "Simple Moving Average";
    }

    @Override
    public String describe() {
        return this + super.toString();
    }
}
