package com.binance.trader.classes;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TickerTest {
    
    @Test
    public void shouldReturnCorrectPrice() {
        Ticker ticker = new Ticker("BTCUSDT", 16000.0);
        assertEquals(ticker.getPrice(), 16000.0, 0);
    }
}
