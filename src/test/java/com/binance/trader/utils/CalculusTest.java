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

        double movingAvg = Calculus.simpleMovingAvg(values);
        assertEquals(5., movingAvg, 0);
    }

    @Test
    public void avgShouldReturnZeroWhenEmpty() {
        ArrayList<Double> values = new ArrayList<Double>();
        double movingAvg = Calculus.simpleMovingAvg(values);
        assertEquals(0, movingAvg, 0);
    }

    @Test
    public void shouldCalculateExpAvg() {
        ArrayList<Double> values = new ArrayList<Double>();
        values.add(5.0);
        values.add(8.0);
        values.add(6.0);
        values.add(7.0);
        values.add(10.0);
        // EMA 3 : smoothing 2 / (n+1) = 0.5 and first SMA = 6.333333333, then 6.6666666, then 8.333333
        assertEquals(8.333, Calculus.lastExpMovingAvg(values), 0.001);
    }

    @Test
    public void expAvgShouldReturnZeroWhenEmpty() {
        ArrayList<Double> values = new ArrayList<Double>();
        assertEquals(0, Calculus.lastExpMovingAvg(values), 0);
    }


}
