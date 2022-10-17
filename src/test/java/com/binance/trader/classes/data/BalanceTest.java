package com.binance.trader.classes.data;

import static org.junit.Assert.assertEquals;

import com.binance.trader.enums.Crypto;
import org.junit.Test;


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
