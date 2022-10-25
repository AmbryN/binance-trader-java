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

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class MACDr2StrategyTest {

    @Mock
    Exchange exchangeMock;

    @InjectMocks MACDr2Strategy strategy;
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
    public void shouldDoNothingIfMACDConsistentlyUnderSignal() {
        // TODO: Write for r2
    }

    @Test
    public void shouldDoNothingIfMACDConsistentlyOverSignal() {
        // TODO: Write a test when MACD is over Signal for longer period
    }

    @Test
    public void shouldBuyIfMACDCrossingUpSignal() {
        // TODO: Write for r2
    }

    @Test
    public void shouldSellIfMACDCrossingDownSignal() {
        // TODO: Write for r2
    }

    private Double[] prepareListOfPricesForDoingNothing() {
       return new Double[] {
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
    }

    private Double[] prepareListOfPricesForBuying() {
        return new Double[] {
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
    }

    private Double[] prepareListOfPricesForSelling() {
        return new Double[] {
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
    }

    public HashMap<String, Double> prepareBalances() {
        HashMap<String, Double> balances = new HashMap<>();
        balances.put("base", 5.);
        balances.put("quote", 1500.);
        return balances;
    }
}