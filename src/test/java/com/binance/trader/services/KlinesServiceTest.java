package com.binance.trader.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.connector.client.impl.spot.Market;
import com.binance.trader.entities.Kline;
import com.binance.trader.enums.Symbol;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class KlinesServiceTest {

    @Mock SpotClientImpl clientMock;
    @Mock Market marketMock;
    
    @InjectMocks KlineService service;

    final Symbol symbol = Symbol.BTCUSDT;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnAValidArrayOfKlines() {
        String answer = "[[1664213621000,\"19034.77000000\",\"19034.77000000\",\"19034.77000000\",\"19034.77000000\",\"0.08661800\",1664213621999,\"1648.75370785\",2,\"0.00000000\",\"0.00000000\",\"0\"]]";
        
        when(clientMock.createMarket()).thenReturn(marketMock);
        when(marketMock.klines(any(LinkedHashMap.class))).thenReturn(answer);

        ArrayList<Kline> klines = service.fetchKlines(symbol);
        Kline expected = new Kline(
            1664213621000L, 
            new BigDecimal("19034.77000000"), 
            new BigDecimal("19034.77000000"),
            new BigDecimal("19034.77000000"), 
            new BigDecimal("19034.77000000"), 
            new BigDecimal("0.08661800"), 
            1664213621999L, 
            new BigDecimal("1648.75370785"), 
            2, 
            new BigDecimal("0.00000000"),
            new BigDecimal("0.00000000")
            );
        boolean test = klines.get(0).equals(expected);
        assertTrue(test);
    }
}
