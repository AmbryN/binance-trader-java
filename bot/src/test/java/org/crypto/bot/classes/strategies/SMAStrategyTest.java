package org.crypto.bot.classes.strategies;

import org.crypto.bot.enums.Period;
import org.crypto.bot.enums.StrategyResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SMAStrategyTest {

    @InjectMocks SMAStrategy strategy;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldBuyIfPriceHigherThanMovingAvg() {
        double[] prices = prepareListOfPricesWithAverage14_95();
        strategy.setPeriod(Period.FiveMinutes);

        StrategyResult result = strategy.execute(15, prices);

        assertEquals(StrategyResult.BUY, result);
    }

    @Test
    public void shouldSellIfPriceLowerThanMovingAvg() {
        double[] prices = prepareListOfPricesWithAverage14_95();
        strategy.setPeriod(Period.FiveMinutes);

        StrategyResult result = strategy.execute(14.5, prices);

        assertEquals(StrategyResult.SELL, result);
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

    public HashMap<String, Double> prepareBalances() {
        HashMap<String, Double> balances = new HashMap<>();
        balances.put("base", 5.);
        balances.put("quote", 100.);
        return balances;
    }
}
