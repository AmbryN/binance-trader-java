package com.binance.trader.classes.strategies;

import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.trader.classes.selectors.IntSelector;
import com.binance.trader.classes.selectors.PeriodListSelector;
import com.binance.trader.classes.strategies.SMAStrategy;
import com.binance.trader.enums.Period;
import com.binance.trader.enums.StrategyResult;
import com.binance.trader.enums.Symbol;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class SMAStrategyTest {

    @Mock SpotClientImpl clientMock;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Ignore
    @Test
    public void shouldBuyIfPriceHigherThanMovingAvg() {
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

        HashMap<String, Double> balances = new HashMap<>();
        balances.put("quote", 100.);

        SMAStrategy strategy = new SMAStrategy();
        SMAStrategy strategySpy = Mockito.spy(strategy);
        Mockito.doReturn(prices).when(strategySpy).getClosePrices(Symbol.BTCUSDT, 10);

        StrategyResult result = strategySpy.execute(Symbol.BTCUSDT, balances, 15);
        assertEquals(StrategyResult.BUY, result);
    }
}
