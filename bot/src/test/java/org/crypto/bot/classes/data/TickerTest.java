package org.crypto.bot.classes.data;


import org.crypto.bot.enums.Symbol;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TickerTest {
    
    @Test
    public void shouldReturnCorrectPriceAndSymbol() {
        Ticker ticker = new Ticker(Symbol.BTCUSDT, 16000.0);
        assertEquals(ticker.getPrice(), 16000.0, 0);
        Assertions.assertEquals(Symbol.BTCUSDT, ticker.getSymbol());
    }
}
