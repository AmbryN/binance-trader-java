package com.binance.trader.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Calculus {
    /**
     * This method takes a list of values and computes
     * @param values the list of values to compute the SMA from
     * @return the SMA
     */
    public static double simpleMovingAvg(Double[] values) {
        if (values.length == 0) {
            return 0.;
        }
        double sum = 0;
        for (double value : values) {
            sum += value;
        }
        return sum / values.length;
    }

    /**
     * This method takes a list of values and computes the last {emaSize}
     * values of the EMA.
     * {emaSize} is the number of periods used to compute the EMA
     * @param values the list of values to compute the EMA from
     * @return EMAList the list of the last {emaSize} values
     */
    public static Double[] expMovingAvg(Double[] values) {
        // Find the middle of the list and ceil it which gives the size (nb of periods) of the EMA
        double middle = (double) values.length / 2.;
        int emaSize = (int) Math.ceil(middle);

        if (values.length == 0) {
            return new Double[] {};
        }
        return expMovingAvgesWithSize(values, emaSize);
    }

    /**
     * Returns an array of the last {emaSize} values of the EMA.
     * @param values the list of values to compute the EMA from
     * @param emaSize is the number of periods used to compute the EMA
     * @return EMAList the list of the last {emaSize} values
     */
    public static Double[] expMovingAvgesWithSize(Double[] values, int emaSize) {
        if (values.length == 0) {
            return new Double[]{0.};
        }

        // Smoothing factor K
        double smoothing = 2 / ( (double) emaSize + 1);

        // First EMA is always the SMA of the {emaSize} first values
        double firstSMA = simpleMovingAvg(Arrays.copyOfRange(values, 0, emaSize));
        ArrayList<Double> EMAList = new ArrayList<>();
        EMAList.add(firstSMA);

        // Sublist of the values which were not used for the SMA and need to be used for following EMAs
        Double[] filteredValues = Arrays.copyOfRange(values, emaSize, values.length);

        // Compute the following EMA from the starting SMA
        double lastEMA = firstSMA;
        for (Double filteredValue : filteredValues) {
            EMAList.add(smoothing * (filteredValue - lastEMA) + lastEMA);
            lastEMA = EMAList.get(EMAList.size() - 1);
        }
        return EMAList.toArray(Double[]::new);
    }
}
