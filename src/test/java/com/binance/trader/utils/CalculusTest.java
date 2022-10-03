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
    public void avgShouldReturnZeroWhenEmpty() {
        ArrayList<Double> values = new ArrayList<Double>();
        double movingAvg = Calculus.calculateAvg(values);
        assertEquals(0, movingAvg, 0);
    }

    @Test
    public void shouldCalculateExpAvg() {
        ArrayList<Double> values = new ArrayList<Double>();
        values.add(2.0);
        values.add(5.0);
        values.add(8.0);
        values.add(6.0);
        values.add(7.0);
        values.add(10.0);
        // EMA 3 : smoothing 2 / (n+1) = 0.5 and first SMA = 5, then 5.5, then 6.25 and 8.125
        assertEquals(8.125, Calculus.calculateExpAvg(values), 0);
    }

    @Test
    public void expAvgShouldReturnZeroWhenEmpty() {
        ArrayList<Double> values = new ArrayList<Double>();
        assertEquals(0, Calculus.calculateExpAvg(values), 0);
    }

    @Test
    public void shouldIgnoreOneValueIfNumberOfValuesUnevenWhenCalculateExpAvg() {
        ArrayList<Double> values = new ArrayList<Double>();
        values.add(5.0);
        values.add(8.0);
        values.add(6.0);
        values.add(7.0);
        values.add(10.0);
        // EMA 3 : smoothing 2 / (n+1) = 0.666 and first SMA = 7, then 7, then 8.998
        assertEquals(9., Calculus.calculateExpAvg(values), 0.001);
    }
}
