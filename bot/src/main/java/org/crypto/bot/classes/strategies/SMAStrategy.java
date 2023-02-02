package org.crypto.bot.classes.strategies;

import org.crypto.bot.utils.Calculus;

public class SMAStrategy extends MovingAverage {

    public SMAStrategy() {
        super();
    }

    @Override
    protected void calculateMovingAvg(double[] closePrices) {
        this.movingAvg = Calculus.simpleMovingAvg(closePrices);
    }

    @Override
    public String toString() {
        return "Simple Moving Avg";
    }
}
