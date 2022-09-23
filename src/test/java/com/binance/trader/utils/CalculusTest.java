package com.binance.trader.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CalculusTest {

    @Test
    public void shoudCalculateAvg() {
        double[] values = new double[]{2.0, 5.0, 8.0};
        double movingAvg = Calculus.calculateAvg(values);
        assertEquals(5., movingAvg, 0);
    }

    @Test
    public void shoudCalculateAvgOfZeroIfNoValues() {
        double[] values = new double[]{};
        double movingAvg = Calculus.calculateAvg(values);
        assertEquals(0, movingAvg, 0);
    }
}
