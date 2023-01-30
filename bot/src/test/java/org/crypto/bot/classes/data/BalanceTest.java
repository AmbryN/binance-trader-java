package org.crypto.bot.classes.data;

import org.crypto.bot.enums.Crypto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BalanceTest {
    
    @Test
    public void shouldReturnBalance() {
        Balance balance = new Balance(Crypto.BTC, 1.0, 0.0);
        double BTCBalance = balance.getFreeBalance();
        assertEquals(1.0, BTCBalance, 0);
    }

    @Test
    public void shouldReturnZeroIfConstructedWithInvalidParams() {
        Balance balance = new Balance(Crypto.BTC, -1.0, 0.0);
        double BTCBalance = balance.getFreeBalance();
        assertEquals(-1.0, BTCBalance, 0);
    }
}
