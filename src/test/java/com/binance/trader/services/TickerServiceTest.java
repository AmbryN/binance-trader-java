package com.binance.trader.services;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.LinkedHashMap;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.binance.connector.client.exceptions.BinanceClientException;
import com.binance.connector.client.exceptions.BinanceConnectorException;
import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.connector.client.impl.spot.Market;
import com.binance.trader.classes.Ticker;
import com.binance.trader.enums.Symbol;
import com.binance.trader.exceptions.BinanceTraderException;

public class TickerServiceTest {
    
    @Mock SpotClientImpl clientMock;
    @Mock Market marketMock;

    @InjectMocks TickerService tickerService;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnThePriceOfSymbol() {
        String answer = "{\"symbol\":\"BTCUSDT\",\"price\":\"4.00000200\"}";
        when(clientMock.createMarket()).thenReturn(marketMock);
        when(marketMock.tickerSymbol(any(LinkedHashMap.class))).thenReturn(answer);

        Ticker expectedTicker = new Ticker("BTCUSDT", 4.00000200);
        assertTrue(expectedTicker.equals(tickerService.getTicker(Symbol.BTCUSDT)));
    }

    @Test(expected = BinanceTraderException.class)
    public void shouldThrowExceptionIfConnectorException() {
        when(clientMock.createMarket()).thenReturn(marketMock);
        when(marketMock.tickerSymbol(any(LinkedHashMap.class))).thenThrow(BinanceConnectorException.class);
        tickerService.getTicker(Symbol.BTCUSDT);
    }

    @Test(expected = BinanceTraderException.class)
    public void shouldThrowExceptionIfClintException() {
        when(clientMock.createMarket()).thenReturn(marketMock);
        when(marketMock.tickerSymbol(any(LinkedHashMap.class))).thenThrow(BinanceClientException.class);
        tickerService.getTicker(Symbol.BTCUSDT);
    }
    
}
