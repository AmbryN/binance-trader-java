package org.crypto.bot.classes.data;

import org.crypto.bot.enums.Crypto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

        Balance balance = accountInfo.getBalance(Crypto.DOGE);
        assertEquals(balance, new Balance(Crypto.DOGE, 0.0, 0.0));
    }
}
