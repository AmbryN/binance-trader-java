package com.binance.trader.classes.enums;

import com.binance.trader.enums.Crypto;
import com.binance.trader.enums.Symbol;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SymbolTest {

    @Test
    public void shouldReturnCorrectBase() {
        assertEquals(Crypto.BTC, Symbol.BTCUSDT.getBase());
        assertEquals(Crypto.DOGE, Symbol.DOGEUSDT.getBase());
    }

    @Test
    public void shouldReturnCorrectQuote() {
        assertEquals(Crypto.USDT, Symbol.BTCUSDT.getQuote());
        assertEquals(Crypto.BUSD, Symbol.ETHBUSD.getQuote());
    }

    @Test
    public void shouldReturnSymbolFromString() {
        assertEquals(Symbol.BTCUSDT, Symbol.toSymbol("BTCUSDT"));
    }

    @Test
    public void shouldReturnNullIfNotFound() {
        assertNull(Symbol.toSymbol("TEST"));
    }
}
