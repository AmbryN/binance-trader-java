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
import static org.mockito.Mockito.when;

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
        String answer = "[[1664203948000,\"0.01634790\",\"0.80000000\",\"0.01575800\",\"0.01577100\",\"148976.11427815\",1664203948999,\"2434.19055334\",308,\"1756.87402397\",\"28.46694368\",\"0\"]]";
        
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        when(clientMock.createMarket()).thenReturn(marketMock);
        when(clientMock.createMarket().klines(parameters)).thenReturn(answer);

        ArrayList<Kline> klines = service.fetchKlines(symbol);
        Kline expected = new Kline(
            1664203948000L, 
            0.01634790, 
            0.80000000,
            0.01575800, 
            0.01577100, 
            148976.11427815, 
            1664203948999L, 
            2434.19055334, 
            308, 
            1756.87402397, 
            28.46694368
            );
        assertEquals(expected, klines.get(0));
    }
}
