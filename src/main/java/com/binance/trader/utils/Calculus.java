package com.binance.trader.utils;

import java.util.ArrayList;

public class Calculus {
    public static double calculateAvg(ArrayList<Double> values) {
        if (values.size() == 0) {
            return 0.;
        }
        double sum = 0;
        for (double value : values) {
            sum += value;
        }
        return sum / values.size();
    }
}
