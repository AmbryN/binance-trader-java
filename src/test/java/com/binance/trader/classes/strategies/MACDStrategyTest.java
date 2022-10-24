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
        Double[] prices = prepareListOfPricesForDoingNothing();
        when(exchangeMock.getClosePrices(any(Symbol.class), any(String.class), any(Integer.class))).thenReturn(prices);
        StrategyResult result = strategy.execute(Symbol.BTCUSDT, 1500.);

        assertEquals(StrategyResult.HOLD, result);
    }

    @Test
    public void shouldBuyIfMACDCrossingUpSignal() {
        HashMap<String, Double> balances = prepareBalances();
        Double[] prices = prepareListOfPricesForBuying();
        when(exchangeMock.getClosePrices(any(Symbol.class), any(String.class), any(Integer.class))).thenReturn(prices);
        StrategyResult result = strategy.execute(Symbol.BTCUSDT, 1500.);

        assertEquals(StrategyResult.BUY, result);
    }

    @Test
    public void shouldSellIfMACDCrossingDownSignal() {
        HashMap<String, Double> balances = prepareBalances();
        Double[] prices = prepareListOfPricesForSelling();
        when(exchangeMock.getClosePrices(any(Symbol.class), any(String.class), any(Integer.class))).thenReturn(prices);
        StrategyResult result = strategy.execute(Symbol.BTCUSDT, 1500.);

        assertEquals(StrategyResult.SELL, result);
    }

    private Double[] prepareListOfPricesForDoingNothing() {
       return new Double[] {
               19170.15,
               19183.83,
               19216.76,
               19201.97,
               19192.32,
               19204.35,
               19195.63,
               19179.22,
               19196.97,
               19197.59,
               19185.28,
               19178.98,
               19177.36,
               19162.37,
               19184.09,
               19184.13,
               19170.01,
               19151.78,
               19150.81,
               19176.27,
               19187.66,
               19190.14,
               19178.34,
               19476.02,
               19437.83,
               19499.83,
               19496.63,
               19538.12,
               19592.96,
               19570.4,
               19518.82,
               19412.47,
               19414.68,
               19391.97,
               19336.06,
               19336.43,
               19295.21,
               19322.4,
               19340.28,
               19382.51,
               19403.21,
               19423.63,
               19373.05,
               19352.4,
               19276.76,
               19301.96,
               19272.81,
               19285.52,
               19334.65,
               19343.23,
               19314.31,
        };
    }

    private Double[] prepareListOfPricesForBuying() {
        return new Double[] {
                19170.15,
                19183.83,
                19216.76,
                19201.97,
                19192.32,
                19204.35,
                19195.63,
                19179.22,
                19196.97,
                19197.59,
                19185.28,
                19178.98,
                19177.36,
                19162.37,
                19184.09,
                19184.13,
                19170.01,
                19151.78,
                19150.81,
                19176.27,
                19187.66,
                19190.14,
                19178.34,
                19476.02,
                19437.83,
                19499.83,
                19496.63,
                19538.12,
                19592.96,
                19570.4,
                19518.82,
                19412.47,
                19414.68,
                19391.97,
                19336.06,
                19336.43,
                19295.21,
                19322.4,
                19340.28,
                19382.51,
                19403.21,
                19423.63,
                19373.05,
                19352.4,
                19276.76,
                19301.96,
                19272.81,
                19300.,
                19400.,
                19500.,
                19500.,
        };
    }

    private Double[] prepareListOfPricesForSelling() {
        return new Double[] {
                19170.15,
                19183.83,
                19216.76,
                19201.97,
                19192.32,
                19204.35,
                19195.63,
                19179.22,
                19196.97,
                19197.59,
                19185.28,
                19178.98,
                19177.36,
                19162.37,
                19184.09,
                19184.13,
                19170.01,
                19151.78,
                19150.81,
                19176.27,
                19187.66,
                19190.14,
                19178.34,
                19476.02,
                19437.83,
                19499.83,
                19496.63,
                19538.12,
                19592.96,
                19570.4,
                19518.82,
                19412.47,
                19414.68,
                19391.97,
                19336.06,
                19336.43,
                19295.21,
                19322.4,
                19340.28,
                19382.51,
                19403.21,
                19423.63,
                19373.05,
                19352.4,
                19276.76,
                19301.96,
                19272.81,
                19300.,
                19400.,
                19600.,
                19300.,
        };
    }

    public HashMap<String, Double> prepareBalances() {
        HashMap<String, Double> balances = new HashMap<>();
        balances.put("base", 5.);
        balances.put("quote", 1500.);
        return balances;
    }
}
