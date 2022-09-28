package com.binance.trader.entities;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class BalanceTest {
    
    @Test
    public void shouldReturnBalance() {
        Balance balance = new Balance("BTC", 1.0, 0.0);
        double BTCBalance = balance.getFreeBalance();
        assertEquals(1.0, BTCBalance, 0);
    }

    @Test
    public void shouldReturnZeroIfConstrucredWithInvalidParams() {
        Balance balance = new Balance("BTC", -1.0, 0.0);
        double BTCBalance = balance.getFreeBalance();
        assertEquals(-1.0, BTCBalance, 0);
    }
}
