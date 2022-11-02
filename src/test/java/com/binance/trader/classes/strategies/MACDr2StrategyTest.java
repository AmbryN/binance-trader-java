package com.binance.trader.classes.strategies;

import com.binance.trader.enums.Period;
import com.binance.trader.enums.StrategyResult;
import com.binance.trader.enums.Symbol;
import com.binance.trader.interfaces.Exchange;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class MACDr2StrategyTest {

    @Mock
    Exchange exchangeMock;

    @InjectMocks MACDr2Strategy strategy;
    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        strategy.setPeriod(Period.FiveMinutes);
        strategy.setShortNbOfPeriods(12);
        strategy.setLongNbOfPeriods(26);
        strategy.setSignalNbOfPeriods(9);
    }

    @Test
    public void shouldDoNothingIfMACDOverSignalForMoreThanTwoPeriods() {
        Double[] prices = prepareListOfPricesForDoingNothingWhenMacdOverSignalForMoreThanTwoPeriods();

        when(exchangeMock.getClosePrices(any(Symbol.class), any(String.class), any(Integer.class))).thenReturn(prices);
        StrategyResult result = strategy.execute(Symbol.BTCUSDT, 19500.);

        assertEquals(StrategyResult.HOLD, result);
    }

    @Test
    public void shouldSellIfMACDUnderSignal() {
        Double[] prices = prepareListOfPricesForSellingIfMacdUnderSignal();

        when(exchangeMock.getClosePrices(any(Symbol.class), any(String.class), any(Integer.class))).thenReturn(prices);
        StrategyResult result = strategy.execute(Symbol.BTCUSDT, 19500.);

        assertEquals(StrategyResult.SELL, result);
    }

    @Test
    public void shouldBuyIfMACDCrossesOverSignalInTheLastPeriodAndMACDUnderZero() {
        Double[] prices = prepareListOfPricesForBuying();

        when(exchangeMock.getClosePrices(any(Symbol.class), any(String.class), any(Integer.class))).thenReturn(prices);
        StrategyResult result = strategy.execute(Symbol.BTCUSDT, 19500.);

        assertEquals(StrategyResult.BUY, result);
    }

    @Test
    public void shouldDoNothingIfMACDCrossesOverSignalInTheLastPeriodButMACDOverZero() {
        Double[] prices = prepareListOfPricesForDoingNothingWhenMACDCrossesSignalInLastPeriodButMacdOverZero();

        when(exchangeMock.getClosePrices(any(Symbol.class), any(String.class), any(Integer.class))).thenReturn(prices);
        StrategyResult result = strategy.execute(Symbol.BTCUSDT, 19500.);

        assertEquals(StrategyResult.HOLD, result);
    }

    private Double[] prepareListOfPricesForSellingIfMacdUnderSignal() {
        return new Double[] {
                19183.68,
                19141.23,
                19133.03,
                19145.94,
                19114.67,
                19185.79,
                19202.48,
                19208.09,
                19134.65,
                19173.18,
                19326.31,
                19253.3,
                19183.91,
                19099.11,
                19062.89,
                19063.82,
                19031.08,
                19073.92,
                19034.04,
                19041.92,
                19049.62,
                19065.24,
                19110.37,
                19051.77,
                19047.46,
                19043.53,
                19036.68,
                19027.86,
                18980.39,
                19011.,
                19003.17,
                18945.65,
                18879.27,
                19065.36,
                18976.14,
                19168.25,
                19127.76,
                19150.98,
                19139.66,
                19189.22,
                19177.82,
                19153.8,
                19189.37,
                19164.37,
                19164.71,
                19155.82,
                19128.58,
                19151.22,
                19148.21,
                19141.13,
                19144.58,
                19163.58,
                19155.28,
                19142.66,
                19167.28,
                19178.66,
                19182.02,
                19196.56,
                19236.2,
                19235.65,
                19193.86,
                19152.02,
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
                19378.53,
                19366.42,
                19361.13,
                19329.72,
                19308.75,
                19297.63,
                19311.9,
                19342.02,
                19341.96,
                19336.61,
                19350.21,
                19350.2,
                19325.98,
                19320.76,
        };
    }

    private Double[] prepareListOfPricesForDoingNothingWhenMacdOverSignalForMoreThanTwoPeriods() {
        return new Double[] {
                19183.68,
                19141.23,
                19133.03,
                19145.94,
                19114.67,
                19185.79,
                19202.48,
                19208.09,
                19134.65,
                19173.18,
                19326.31,
                19253.3,
                19183.91,
                19099.11,
                19062.89,
                19063.82,
                19031.08,
                19073.92,
                19034.04,
                19041.92,
                19049.62,
                19065.24,
                19110.37,
                19051.77,
                19047.46,
                19043.53,
                19036.68,
                19027.86,
                18980.39,
                19011.,
                19003.17,
                18945.65,
                18879.27,
                19065.36,
                18976.14,
                19168.25,
                19127.76,
                19150.98,
                19139.66,
                19189.22,
                19177.82,
                19153.8,
                19189.37,
                19164.37,
                19164.71,
                19155.82,
                19128.58,
                19151.22,
                19148.21,
                19141.13,
                19144.58,
                19163.58,
                19155.28,
                19142.66,
                19167.28,
                19178.66,
                19182.02,
                19196.56,
                19236.2,
                19235.65,
                19193.86,
                19152.02,
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
                19378.53,
                19366.42,
                19361.13,
                19329.72,
                19308.75,
                19297.63,
                19311.9,
                19342.02,
                19341.96,
                19336.61,
                19329.98,
                19320.9,
                19422.27,
                19421.98,
        };
    }

    private Double[] prepareListOfPricesForSellingWhenTickerGoesUnderBuyPrice() {
        return new Double[] {
                19183.68,
                19141.23,
                19133.03,
                19145.94,
                19114.67,
                19185.79,
                19202.48,
                19208.09,
                19134.65,
                19173.18,
                19326.31,
                19253.3,
                19183.91,
                19099.11,
                19062.89,
                19063.82,
                19031.08,
                19073.92,
                19034.04,
                19041.92,
                19049.62,
                19065.24,
                19110.37,
                19051.77,
                19047.46,
                19043.53,
                19036.68,
                19027.86,
                18980.39,
                19011.,
                19003.17,
                18945.65,
                18879.27,
                19065.36,
                18976.14,
                19168.25,
                19127.76,
                19150.98,
                19139.66,
                19189.22,
                19177.82,
                19153.8,
                19189.37,
                19164.37,
                19164.71,
                19155.82,
                19128.58,
                19151.22,
                19148.21,
                19141.13,
                19144.58,
                19163.58,
                19155.28,
                19142.66,
                19167.28,
                19178.66,
                19182.02,
                19196.56,
                19236.2,
                19235.65,
                19193.86,
                19152.02,
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
                19378.53,
                19366.42,
                19361.13,
                19329.72,
                19308.75,
                19297.63,
                19311.9,
                19342.02,
                19341.96,
                19336.61,
                19350.12,
                19353.21,
                19352.27,
                19349.5,
        };
    }

    private Double[] prepareListOfPricesForBuying() {
        return new Double[] {
                19183.68,
                19141.23,
                19133.03,
                19145.94,
                19114.67,
                19185.79,
                19202.48,
                19208.09,
                19134.65,
                19173.18,
                19326.31,
                19253.3,
                19183.91,
                19099.11,
                19062.89,
                19063.82,
                19031.08,
                19073.92,
                19034.04,
                19041.92,
                19049.62,
                19065.24,
                19110.37,
                19051.77,
                19047.46,
                19043.53,
                19036.68,
                19027.86,
                18980.39,
                19011.,
                19003.17,
                18945.65,
                18879.27,
                19065.36,
                18976.14,
                19168.25,
                19127.76,
                19150.98,
                19139.66,
                19189.22,
                19177.82,
                19153.8,
                19189.37,
                19164.37,
                19164.71,
                19155.82,
                19128.58,
                19151.22,
                19148.21,
                19141.13,
                19144.58,
                19163.58,
                19155.28,
                19142.66,
                19167.28,
                19178.66,
                19182.02,
                19196.56,
                19236.2,
                19235.65,
                19193.86,
                19152.02,
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
                19378.53,
                19366.42,
                19361.13,
                19329.72,
                19308.75,
                19297.63,
                19311.9,
                19342.02,
                19341.96,
                19336.61,
                19329.98,
                19306.75,
                19326.98,
                19355.25,
        };
    }

    private Double[] prepareListOfPricesForDoingNothingWhenMACDCrossesSignalInLastPeriodButMacdOverZero() {
        return new Double[] {
                19183.68,
                19141.23,
                19133.03,
                19145.94,
                19114.67,
                19185.79,
                19202.48,
                19208.09,
                19134.65,
                19173.18,
                19326.31,
                19253.3,
                19183.91,
                19099.11,
                19062.89,
                19063.82,
                19031.08,
                19073.92,
                19034.04,
                19041.92,
                19049.62,
                19065.24,
                19110.37,
                19051.77,
                19047.46,
                19043.53,
                19036.68,
                19027.86,
                18980.39,
                19011.,
                19003.17,
                18945.65,
                18879.27,
                19065.36,
                18976.14,
                19168.25,
                19127.76,
                19150.98,
                19139.66,
                19189.22,
                19177.82,
                19153.8,
                19189.37,
                19164.37,
                19164.71,
                19155.82,
                19128.58,
                19151.22,
                19148.21,
                19141.13,
                19144.58,
                19163.58,
                19155.28,
                19142.66,
                19167.28,
                19178.66,
                19182.02,
                19196.56,
                19236.2,
                19235.65,
                19193.86,
                19152.02,
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
                19378.53,
                19366.42,
                19361.13,
                19329.72,
                19308.75,
                19297.63,
                19350.32,
                19400.25,
                19450.18,
                19370.15,
                19315.23,
                19290.87,
                19340.25,
                19390.56,
        };
    }
}
