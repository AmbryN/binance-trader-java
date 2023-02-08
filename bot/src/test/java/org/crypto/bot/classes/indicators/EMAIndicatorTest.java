package org.crypto.bot.classes.indicators;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EMAIndicatorTest {
    @Test
    public void shouldReturnTheCorrectEMA() {
        double[] prices = prepareListOfPricesWithExpAverage16_237();
        EMAIndicator emaIndicator = new EMAIndicator(new ClosePriceIndicator(), 5);

        assertEquals(16.237, emaIndicator.getLastValue(prices), 0.001);
    }

    @Test
    public void shouldReturnZero() {
        double[] pricesEmpty = new double[9];
        EMAIndicator emaIndicator = new EMAIndicator(new ClosePriceIndicator(), 5);

        assertEquals(0, emaIndicator.getLastValue(pricesEmpty), 0);
    }

    public double[] prepareListOfPricesWithExpAverage16_237() {
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
        };
    }
}
