package com.binance.trader.classes.data;

import static org.junit.Assert.assertEquals;

import com.binance.trader.classes.data.Ticker;
import com.binance.trader.enums.Symbol;
import org.junit.Test;

public class TickerTest {
    
    @Test
    public void shouldReturnCorrectPriceAndSymbol() {
        Ticker ticker = new Ticker(Symbol.BTCUSDT, 16000.0);
        assertEquals(ticker.getPrice(), 16000.0, 0);
        assertEquals(Symbol.BTCUSDT, ticker.getSymbol());
    }
}
