package org.crypto.bot.classes.indicators;

import org.crypto.bot.enums.Period;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EMAIndicatorTest {
    @Test
    public void shouldReturnTheCorrectEMA() {
        double[] prices = prepareListOfPricesWithExpAverage16_237();
        EMAIndicator emaIndicator = new EMAIndicator(5);

        assertEquals(16.237, emaIndicator.getValue(prices), 0.001);
    }

    @Test
    public void shouldReturnZero() {
        double[] pricesEmpty = new double[9];
        EMAIndicator emaIndicator = new EMAIndicator(5);

        assertEquals(0, emaIndicator.getValue(pricesEmpty), 0);
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
