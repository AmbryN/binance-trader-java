package org.crypto.bot.classes.enums;

import org.crypto.bot.enums.Crypto;
import org.crypto.bot.enums.Symbol;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SymbolTest {

    @Test
    public void shouldReturnCorrectBase() {
        Assertions.assertEquals(Crypto.BTC, Symbol.BTCUSDT.getBase());
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
    public void shouldThrowIllegalArgumentExceptionIfNotFound() {
        assertThrows(IllegalArgumentException.class, () -> Symbol.toSymbol("TEST"));
    }
}
