package com.binance.trader.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.LinkedHashMap;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.connector.client.impl.spot.Market;
import com.binance.trader.classes.data.Ticker;
import com.binance.trader.enums.Symbol;

public class TickerServiceTest {
    
    @Mock SpotClientImpl clientMock;
    @Mock Market marketMock;

    @InjectMocks
    TickerService tickerService;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnThePriceOfSymbol() {
        String answer = "{\"symbol\":\"BTCUSDT\",\"price\":\"4.00000200\"}";
        when(clientMock.createMarket()).thenReturn(marketMock);
        when(marketMock.tickerSymbol(any(LinkedHashMap.class))).thenReturn(answer);

        Ticker expectedTicker = new Ticker(Symbol.BTCUSDT, 4.00000200);
        assertEquals(expectedTicker, tickerService.getTicker(Symbol.BTCUSDT));
    }
}
