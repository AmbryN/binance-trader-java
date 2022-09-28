package com.binance.trader.entities;

public class AccountInfo {
    private int makerCommission;
    private int takerCommission;
    private int buyerCommission;
    private int sellerCommission;
    private boolean canTrade;
    private boolean canWithdraw;
    private boolean canDeposit;
    private boolean brokered;
    private Long updateTime;
    private String accountType;
    private Balance[] balances;
    private String[] permissions;

    AccountInfo(Balance[] balances) {
        this.balances = balances;
    }

    public Balance getBalance(String symbol) {
        for (Balance balance : balances) {
            if (balance.getAsset().equals(symbol)) {
                return balance;
            }
        }
        return new Balance(symbol, 0.0, 0.0);
    }
}
