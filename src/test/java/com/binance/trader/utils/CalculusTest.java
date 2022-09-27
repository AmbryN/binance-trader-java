package com.binance.trader.utils;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

public class CalculusTest {

    @Test
    public void shoudCalculateAvg() {
        ArrayList<Double> values = new ArrayList<Double>();
        values.add(2.0);
        values.add(5.0);
        values.add(8.0);

        double movingAvg = Calculus.calculateAvg(values);
        assertEquals(5., movingAvg, 0);
    }

    @Test
    public void shoudCalculateAvgOfZeroIfNoValues() {
        ArrayList<Double> values = new ArrayList<Double>();
        double movingAvg = Calculus.calculateAvg(values);
        assertEquals(0, movingAvg, 0);
    }
}
