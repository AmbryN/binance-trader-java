package org.crypto.bot.classes.indicators;

public class ConstantIndicator implements Indicator{
    private final double value;

    public ConstantIndicator(double value) {
        this.value = value;
    }

    @Override
    public double getValue(double[] prices) {
        return value;
    }

    @Override
    public int getNbOfRecordsToFetch() {
        return 0;
    }

    @Override
    public String describe() {
        return "Constant Value: " + value;
    }
}
