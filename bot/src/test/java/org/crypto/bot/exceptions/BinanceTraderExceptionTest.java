package org.crypto.bot.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BinanceTraderExceptionTest {

    @Test
    public void shouldReturnTheExceptionMessage() {
        BinanceTraderException ex = new BinanceTraderException("Test message");
        assertEquals("Test message", ex.getMessage());
    }
}
