package org.crypto.bot.classes.indicators;

import org.crypto.bot.enums.Period;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SMAIndicatorTest {

    @Test
    public void shouldReturnCorrectSMA() {
        double[] prices = prepareListOfPricesWithAverage14_95();
        SMAIndicator smaIndicator = new SMAIndicator(10);

        assertEquals(smaIndicator.getValue(prices), 14.95);
    }

    @Test
    public void shouldReturnZero() {
        double[] pricesEmpty = new double[10];
        SMAIndicator smaIndicator = new SMAIndicator(10);

        assertEquals(0, smaIndicator.getValue(pricesEmpty), 0);
    }


    public double[] prepareListOfPricesWithAverage14_95() {
        return new double[]{
                10.0,
                11.0,
                12.5,
                15.,
                15.,
                16.,
                16.,
                17.,
                18.,
                19.,
        };
    }
}
