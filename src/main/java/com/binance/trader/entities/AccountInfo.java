package com.binance.trader.entities;

public class AccountInfo {
    int makerCommission;
    int takerCommission;
    int buyerCommission;
    int sellerCommission;
    boolean carTrader;
    boolean canWithdraw;
    boolean canDeposit;
    boolean brokered;
    Long updateTime;
    String accountType;
    Balance[] balances;
    String[] permissions;

    AccountInfo() {}

    public Balance getBalance(String symbol) {
        for (Balance balance : balances) {
            if (balance.asset.equals(symbol)) {
                return balance;
            }
        }
        return new Balance(symbol, "0", "0");
    }
}
