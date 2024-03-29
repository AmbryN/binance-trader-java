package org.crypto.bot.utils;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculusTest {

    @Test
    public void shoudCalculateAvg() {
        double[] values = new double[]{
                2.0,
                5.0,
                8.0
        };

        double movingAvg = Calculus.simpleMovingAvg(values);
        assertEquals(5., movingAvg, 0);
    }

    @Test
    public void avgShouldReturnZeroWhenEmpty() {
        double[] values = new double[]{};
        double movingAvg = Calculus.simpleMovingAvg(values);
        assertEquals(0, movingAvg, 0);
    }

    @Test
    public void shouldCalculateExpAvg() {
        double[] values = new double[]{
                5.0,
                8.0,
                6.0,
                7.0,
                10.0,
        };
        // EMA 3 : smoothing 2 / (n+1) = 0.5 and first SMA = 6.333333333, then 6.6666666, then 8.333333
        double[] emas = Calculus.expMovingAvgesWithSize(values, 3);
        double lastEMA = emas[emas.length -1];
        assertEquals(8.333, lastEMA, 0.001);
    }

    @Test
    public void expAvgShouldReturnZeroWhenEmpty() {
        double[] values = new double[] {};
        double[] emas = Calculus.expMovingAvgesWithSize(values, 3);
        double lastEMA = emas[emas.length -1];
        assertEquals(0, lastEMA, 0);
    }

    @Test
    public void shouldCalculateAllExpAvg() {
        double[] values = new double[] {
                5.0,
                8.0,
                6.0,
                7.0,
                10.0,
        };
        // EMA 3 : smoothing 2 / (n+1) = 0.5 and first SMA = 6.333333333, then 6.6666666, then 8.333333
        ArrayList<Double> expected = new ArrayList<Double>();
        expected.add(6.333333);
        expected.add(6.666666);
        expected.add(8.333333);
        double[] result = Calculus.expMovingAvgesWithSize(values, 3);
        for(int i=0; i<expected.size(); i++) {
            assertEquals(expected.get(i), result[i], 0.000001);
        }
    }

    @Test
    public void shouldCalculateAllExpAvgSizeThree() {
        double[] values = new double[]{
                5.0,
                8.0,
                6.0,
                7.0,
                10.0,
                11.0,
                15.0,
        };
        // EMA 3 : smoothing 2 / (n+1) = 0.5 and first SMA = 6.333333333, then 6.6666666, then 8.333333, then 9.666666,
        // then 12.333333
        double[] expected = new double[] {
                6.333333,
                6.666666,
                8.333333,
                9.666666,
                12.333333,
        };
        double[] result = Calculus.expMovingAvgesWithSize(values, 3);
        for (int i=0; i<expected.length; i++) {
            assertEquals(expected[i], result[i], 0.000001);
        }
    }


}
