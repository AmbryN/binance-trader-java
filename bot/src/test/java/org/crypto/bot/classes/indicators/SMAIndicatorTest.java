package org.crypto.bot.classes.indicators;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SMAIndicatorTest {

    @Test
    public void shouldReturnCorrectSMA() {
        double[] prices = prepareListOfPricesWithAverage14_95();
        SMAIndicator smaIndicator = new SMAIndicator(new ClosePriceIndicator(), 10);

        assertEquals(smaIndicator.getLastValue(prices), 14.95);
    }

    @Test
    public void shouldReturnZero() {
        double[] pricesEmpty = new double[10];
        SMAIndicator smaIndicator = new SMAIndicator(new ClosePriceIndicator(), 10);

        assertEquals(0, smaIndicator.getLastValue(pricesEmpty), 0);
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
