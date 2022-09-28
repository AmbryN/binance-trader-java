package com.binance.trader.classes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AccountInfoTest {
    
    @Test
    public void shouldReturnCorrectBalance() {
        Balance selectedBalance = new Balance("BTC", 1.0, 0.0);

        Balance[] balances = new Balance[] {selectedBalance};

        AccountInfo accountInfo = new AccountInfo(balances);

        Balance balance = accountInfo.getBalance("BTC");
        assertEquals(selectedBalance, balance);
    }

    @Test
    public void shouldReturnEmptyBalanceIfInexistant() {
        Balance selectedBalance = new Balance("BTC", 1.0, 0.0);

        Balance[] balances = new Balance[] {selectedBalance};

        AccountInfo accountInfo = new AccountInfo(balances);

        Balance balance = accountInfo.getBalance("HAHA");
        assertTrue(balance.equals(new Balance("HAHA", 0.0, 0.0)));
    }
}
