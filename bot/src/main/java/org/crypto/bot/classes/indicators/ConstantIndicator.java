package org.crypto.bot.classes.indicators;

public class ConstantIndicator implements Indicator{
    private double value;

    public ConstantIndicator() {
        super();
    }
    /**
     * Creates a constant indicator from the
     * value passed as an argument.
     * @param value used as constant
     */
    public ConstantIndicator(double value) {
        this.value = value;
    }

    /**
     * Creates a constant indicator with the last value
     * of the array passed as argument.
     * @param values array of values
     */
    public ConstantIndicator(double[] values) {
        this.value = values[values.length - 1];
    }

    /**
     * Gets the value the ConstantIndicator is set at
     * @param prices (unused in this case)
     * @return the value the Indicator is set at
     */
    @Override
    public double getValue(double[] prices) {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public int getNbOfRecordsToFetch() {
        return 0;
    }

    @Override
    public String toString() {
        return "(Constant Indicator: " + value + ")";
    }
}
