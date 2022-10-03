package com.binance.trader.utils;

import java.util.ArrayList;
import java.util.List;

public class Calculus {
    public static double calculateAvg(List<Double> values) {
        if (values.size() == 0) {
            return 0.;
        }
        double sum = 0;
        for (double value : values) {
            sum += value;
        }
        return sum / values.size();
    }

    public static double calculateExpAvg(List<Double> values) {
        if (values.size() == 0) {
            return 0.;
        }
        double middle = (double) values.size() / 2.;
        int emaSize = (int) Math.floor(middle);

        double smoothing = 2 / ( (double) emaSize + 1);

        double firstSMA;
        if (middle == emaSize) {
            firstSMA = calculateAvg(values.subList(0, emaSize));
            return calculateEMA(
                    values.subList(emaSize, values.size()),
                    smoothing,
                    firstSMA);
        } else {
            firstSMA = calculateAvg(values.subList(1, emaSize+1));
            return calculateEMA(
                    values.subList(emaSize+1, values.size()),
                    smoothing,
                    firstSMA);
        }
    }

    private static double calculateEMA(List<Double> values, double smoothing, double firstSMA) {
        ArrayList<Double> EMAList = new ArrayList<>();
        double lastEMA = firstSMA;
        for (int i=0; i < values.size(); i++) {
            EMAList.add(smoothing * (values.get(i) - lastEMA) + lastEMA);
            lastEMA = EMAList.get(EMAList.size() - 1);
        }
        return EMAList.get(EMAList.size() - 1);
    }
}
