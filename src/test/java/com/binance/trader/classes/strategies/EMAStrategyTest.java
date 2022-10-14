package com.binance.trader.classes.strategies;

import com.binance.trader.enums.Period;
import com.binance.trader.enums.StrategyResult;
import com.binance.trader.enums.Symbol;
import com.binance.trader.interfaces.Exchange;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class EMAStrategyTest {

    @Mock
    Exchange exchangeMock;
    @InjectMocks EMAStrategy strategy;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldBuyIfPriceHigherThanExpMovingAvg() {
        ArrayList<Double> prices = prepareListOfPricesWithExpAverage16_237();
        HashMap<String, Double> balances = prepareBalances();
        strategy.setPeriod(Period.FiveMinutes);

        when(exchangeMock.getClosePrices(any(Symbol.class), any(String.class), any(Integer.class))).thenReturn(prices);
        StrategyResult result = strategy.execute(Symbol.BTCUSDT, balances, 16.5);

        assertEquals(StrategyResult.BUY, result);
    }

    @Test
    public void shouldSellIfPriceLowerThanExpMovingAvg() {
        ArrayList<Double> prices = prepareListOfPricesWithExpAverage16_237();
        HashMap<String, Double> balances = prepareBalances();
        strategy.setPeriod(Period.FiveMinutes);

        when(exchangeMock.getClosePrices(any(Symbol.class), any(String.class), any(Integer.class))).thenReturn(prices);
        StrategyResult result = strategy.execute(Symbol.BTCUSDT, balances, 16.0);

        assertEquals(StrategyResult.SELL, result);
    }

    public ArrayList<Double> prepareListOfPricesWithExpAverage16_237() {
        ArrayList<Double> prices = new ArrayList<>();
        prices.add(10.0);
        prices.add(11.0);
        prices.add(12.5);
        prices.add(15.);
        prices.add(15.);
        prices.add(16.);
        prices.add(16.);
        prices.add(17.);
        prices.add(18.);
        return prices;
    }

    public HashMap<String, Double> prepareBalances() {
        HashMap<String, Double> balances = new HashMap<>();
        balances.put("base", 5.);
        balances.put("quote", 100.);
        return balances;
    }
}