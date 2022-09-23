package com.binance.trader.utils;

public class Calculus {
    public static double calculateAvg(double[] values) {
        if (values.length == 0) {
            return 0.;
        }
        double sum = 0;
        for (double value : values) {
            sum += value;
        }
        return sum / values.length;
    }
}
