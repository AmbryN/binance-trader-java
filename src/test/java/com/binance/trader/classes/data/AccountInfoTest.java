package com.binance.trader.classes.data;

import static org.junit.Assert.assertEquals;

import com.binance.trader.enums.Crypto;
import org.junit.Test;

public class AccountInfoTest {
    
    @Test
    public void shouldReturnCorrectBalance() {
        Balance selectedBalance = new Balance(Crypto.BTC, 1.0, 0.0);

        Balance[] balances = new Balance[] {selectedBalance};

        AccountInfo accountInfo = new AccountInfo(balances);

        Balance balance = accountInfo.getBalance(Crypto.BTC);
        assertEquals(selectedBalance, balance);
    }

    @Test
    public void shouldReturnEmptyBalanceIfInexistant() {
        AccountInfo accountInfo = new AccountInfo(new Balance[] {});

        Balance balance = accountInfo.getBalance(Crypto.LUNA);
        assertEquals(balance, new Balance(Crypto.LUNA, 0.0, 0.0));
    }
}
