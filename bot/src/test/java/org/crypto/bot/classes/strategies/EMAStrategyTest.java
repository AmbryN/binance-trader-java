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

    @Mock
    Exchange exchangeMock;
    @InjectMocks EMAStrategy strategy;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldBuyIfPriceHigherThanExpMovingAvg() {
        Double[] prices = prepareListOfPricesWithExpAverage16_237();
        HashMap<String, Double> balances = prepareBalances();
        strategy.setPeriod(Period.FiveMinutes);
        strategy.setNbOfPeriods(5);

        when(exchangeMock.getClosePrices(any(Symbol.class), any(String.class), any(Integer.class))).thenReturn(prices);
        StrategyResult result = strategy.execute(Symbol.BTCUSDT, 16.5);

        assertEquals(StrategyResult.BUY, result);
    }

    @Test
    public void shouldSellIfPriceLowerThanExpMovingAvg() {
        Double[] prices = prepareListOfPricesWithExpAverage16_237();
        HashMap<String, Double> balances = prepareBalances();
        strategy.setPeriod(Period.FiveMinutes);

        when(exchangeMock.getClosePrices(any(Symbol.class), any(String.class), any(Integer.class))).thenReturn(prices);
        StrategyResult result = strategy.execute(Symbol.BTCUSDT, 16.0);

        assertEquals(StrategyResult.SELL, result);
    }

    public Double[] prepareListOfPricesWithExpAverage16_237() {
        return new Double[]{
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
