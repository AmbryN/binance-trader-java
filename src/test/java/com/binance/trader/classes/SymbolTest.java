package com.binance.trader.classes;

import com.binance.trader.enums.Crypto;
import com.binance.trader.enums.Symbol;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SymbolTest {

    @Test
    public void shouldReturnCorrectBase() {
        assertEquals(Crypto.BTC, Symbol.BTCUSDT.getBase());
        assertEquals(Crypto.LUNA, Symbol.LUNAUSDT.getBase());
    }

    @Test
    public void shouldReturnCorrectQuote() {
        assertEquals(Crypto.USDT, Symbol.BTCUSDT.getQuote());
        assertEquals(Crypto.BUSD, Symbol.ETHBUSD.getQuote());
    }
}
