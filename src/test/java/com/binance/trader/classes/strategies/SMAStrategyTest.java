package com.binance.trader.classes.strategies;

import com.binance.trader.enums.Period;
import com.binance.trader.enums.StrategyResult;
import com.binance.trader.enums.Symbol;
import com.binance.trader.interfaces.Exchange;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class SMAStrategyTest {

    @Mock
    Exchange exchangeMock;
    @InjectMocks SMAStrategy strategy;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldBuyIfPriceHigherThanMovingAvg() {
        ArrayList<Double> prices = prepareListOfPricesWithAverage14_95();
        HashMap<String, Double> balances = prepareBalances();
        strategy.setPeriod(Period.FiveMinutes);

        when(exchangeMock.getClosePrices(any(Symbol.class), any(String.class), any(Integer.class))).thenReturn(prices);
        StrategyResult result = strategy.execute(Symbol.BTCUSDT, balances, 15);

        assertEquals(StrategyResult.BUY, result);
    }

    @Test
    public void shouldSellIfPriceLowerThanMovingAvg() {
        ArrayList<Double> prices = prepareListOfPricesWithAverage14_95();
        HashMap<String, Double> balances = prepareBalances();
        strategy.setPeriod(Period.FiveMinutes);

        when(exchangeMock.getClosePrices(any(Symbol.class), any(String.class), any(Integer.class))).thenReturn(prices);
        StrategyResult result = strategy.execute(Symbol.BTCUSDT, balances, 14.5);

        assertEquals(StrategyResult.SELL, result);
    }

    public ArrayList<Double> prepareListOfPricesWithAverage14_95() {
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
        prices.add(19.);
        return prices;
    }

    public HashMap<String, Double> prepareBalances() {
        HashMap<String, Double> balances = new HashMap<>();
        balances.put("base", 5.);
        balances.put("quote", 100.);
        return balances;
    }
}
