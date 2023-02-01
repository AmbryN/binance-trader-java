package org.crypto.bot.classes.strategies;

import org.crypto.bot.enums.Period;
import org.crypto.bot.enums.StrategyResult;
import org.crypto.bot.enums.Symbol;
import org.crypto.bot.interfaces.Exchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class EMAStrategyTest {

    @InjectMocks EMAStrategy strategy;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldBuyIfPriceHigherThanExpMovingAvg() {
        double[] prices = prepareListOfPricesWithExpAverage16_237();
        strategy.setPeriod(Period.FiveMinutes);
        strategy.setNbOfPeriods(5);

        StrategyResult result = strategy.execute(16.5, prices);

        assertEquals(StrategyResult.BUY, result);
    }

    @Test
    public void shouldSellIfPriceLowerThanExpMovingAvg() {
        double[] prices = prepareListOfPricesWithExpAverage16_237();
        strategy.setPeriod(Period.FiveMinutes);

        StrategyResult result = strategy.execute(16.0, prices);

        assertEquals(StrategyResult.SELL, result);
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

    public HashMap<String, Double> prepareBalances() {
        HashMap<String, Double> balances = new HashMap<>();
        balances.put("base", 5.);
        balances.put("quote", 100.);
        return balances;
    }
}
