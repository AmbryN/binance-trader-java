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
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class MACDStrategyTest {

    @Mock
    Exchange exchangeMock;

    @InjectMocks MACDStrategy strategy;
    HashMap<String, Double> balances;
    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        strategy.setPeriod(Period.FiveMinutes);
        strategy.setShortNbOfPeriods(12);
        strategy.setLongNbOfPeriods(26);
        strategy.setSignalNbOfPeriods(9);
        balances = prepareBalances();
    }

    @Test
    public void shouldDoNothingIfMACDUnderSignal() {
        ArrayList<Double> prices = prepareListOfPricesForDoingNothing();
        when(exchangeMock.getClosePrices(any(Symbol.class), any(String.class), any(Integer.class))).thenReturn(prices);
        StrategyResult result = strategy.execute(Symbol.BTCUSDT, balances, 1500.);

        assertEquals(StrategyResult.NONE, result);
    }

    @Test
    public void shouldBuyIfMACDCrossingUpSignal() {
        HashMap<String, Double> balances = prepareBalances();
        ArrayList<Double> prices = prepareListOfPricesForBuying();
        when(exchangeMock.getClosePrices(any(Symbol.class), any(String.class), any(Integer.class))).thenReturn(prices);
        StrategyResult result = strategy.execute(Symbol.BTCUSDT, balances, 1500.);

        assertEquals(StrategyResult.BUY, result);
    }

    @Test
    public void shouldSellIfMACDCrossingDownSignal() {
        HashMap<String, Double> balances = prepareBalances();
        ArrayList<Double> prices = prepareListOfPricesForSelling();
        when(exchangeMock.getClosePrices(any(Symbol.class), any(String.class), any(Integer.class))).thenReturn(prices);
        StrategyResult result = strategy.execute(Symbol.BTCUSDT, balances, 1500.);

        assertEquals(StrategyResult.SELL, result);
    }

    private ArrayList<Double> prepareListOfPricesForDoingNothing() {
        Double[] pricesArray = new Double[] {
                1326.09,
                1317.81,
                1317.96,
                1323.66,
                1318.22,
                1315.5,
                1323.31,
                1321.73,
                1319.83,
                1321.62,
                1329.36,
                1328.59,
                1325.62,
                1338.1,
                1350.4,
                1348.17,
                1348.12,
                1349.15,
                1350.,
                1354.18,
                1353.61,
                1351.33,
                1354.33,
                1347.8,
                1347.71,
                1350.71,
                1354.99,
                1361.6,
                1358.05,
                1359.43,
                1361.85,
                1354.47,
                1354.88,
                1356.56,
                1354.36,
                1350.88,
                1352.87,
                1356.77,
                1353.5,
                1344.73,
                1343.9,
                1346.1,
        };
        return new ArrayList<>(List.of(pricesArray));
    }

    private ArrayList<Double> prepareListOfPricesForBuying() {
        Double[] pricesArray = new Double[] {
                1326.09,
                1317.81,
                1317.96,
                1323.66,
                1318.22,
                1315.5,
                1323.31,
                1321.73,
                1319.83,
                1321.62,
                1329.36,
                1328.59,
                1325.62,
                1338.1,
                1350.4,
                1348.17,
                1348.12,
                1349.15,
                1350.,
                1354.18,
                1353.61,
                1351.33,
                1354.33,
                1347.8,
                1347.71,
                1350.71,
                1354.99,
                1361.6,
                1358.05,
                1359.43,
                1361.85,
                1354.47,
                1354.88,
                1356.56,
                1354.36,
                1350.88,
                1352.87,
                1356.77,
                1353.5,
                1360.0,
                1380.0,
                1390.0,
        };
        return new ArrayList<>(List.of(pricesArray));
    }

    private ArrayList<Double> prepareListOfPricesForSelling() {
        Double[] pricesArray = new Double[] {
                1326.09,
                1317.81,
                1317.96,
                1323.66,
                1318.22,
                1315.5,
                1323.31,
                1321.73,
                1319.83,
                1321.62,
                1329.36,
                1328.59,
                1325.62,
                1338.1,
                1350.4,
                1348.17,
                1348.12,
                1349.15,
                1350.,
                1354.18,
                1353.61,
                1351.33,
                1354.33,
                1347.8,
                1347.71,
                1350.71,
                1354.99,
                1361.6,
                1358.05,
                1359.43,
                1361.85,
                1354.47,
                1354.88,
                1356.56,
                1390.0,
                1390.0,
                1390.0,
                1390.0,
                1390.0,
                1390.0,
                1390.0,
                1370.0
        };
        return new ArrayList<>(List.of(pricesArray));
    }

    public HashMap<String, Double> prepareBalances() {
        HashMap<String, Double> balances = new HashMap<>();
        balances.put("base", 5.);
        balances.put("quote", 1500.);
        return balances;
    }
}
