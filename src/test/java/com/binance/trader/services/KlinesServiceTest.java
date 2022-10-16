package com.binance.trader.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.connector.client.impl.spot.Market;
import com.binance.trader.classes.data.Kline;
import com.binance.trader.enums.Symbol;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class KlinesServiceTest {

    @Mock SpotClientImpl clientMock;
    @Mock Market marketMock;
    
    @InjectMocks
    KlineService service;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnAValidArrayOfKlines() {
        String answer = "[[1664213621000,\"19034.77000000\",\"19034.77000000\",\"19034.77000000\",\"19034.77000000\",\"0.08661800\",1664213621999,\"1648.75370785\",2,\"0.00000000\",\"0.00000000\",\"0\"]]";
        when(clientMock.createMarket()).thenReturn(marketMock);
        when(marketMock.klines(any(LinkedHashMap.class))).thenReturn(answer);

        ArrayList<Kline> klines = service.fetchKlines(Symbol.BTCUSDT, "1h", 1);
        Kline expected = new Kline(
            1664213621000L, 
            19034.77000000, 
            19034.77000000,
            19034.77000000, 
            19034.77000000, 
            0.08661800, 
            1664213621999L, 
            1648.75370785, 
            2, 
            0.00000000,
            0.00000000
            );
        boolean test = klines.get(0).equals(expected);
        assertTrue(test);
    }
}
