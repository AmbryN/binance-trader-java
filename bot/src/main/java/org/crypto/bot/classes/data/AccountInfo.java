package org.crypto.bot.classes.data;

import org.crypto.bot.enums.Crypto;
import org.crypto.bot.enums.Symbol;

import java.util.HashMap;

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
    private final Balance[] balances;
    private String[] permissions;

    protected AccountInfo(Balance[] balances) {
        this.balances = balances;
    }

    public int getMakerCommission() {
        return makerCommission;
    }

    public int getTakerCommission() {
        return takerCommission;
    }

    public int getBuyerCommission() {
        return buyerCommission;
    }

    public int getSellerCommission() {
        return sellerCommission;
    }

    public boolean isCanTrade() {
        return canTrade;
    }

    public boolean isCanWithdraw() {
        return canWithdraw;
    }

    public boolean isCanDeposit() {
        return canDeposit;
    }

    public boolean isBrokered() {
        return brokered;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public String getAccountType() {
        return accountType;
    }

    public Balance[] getBalances() {
        return balances;
    }

    public String[] getPermissions() {
        return permissions;
    }

    public Balance getBalance(Crypto crypto) {
        for (Balance balance : balances) {
            Crypto asset = balance.getAsset();
            if (asset != null && balance.getAsset().equals(crypto)) {
                return balance;
            }
        }
        return new Balance(crypto, 0.0, 0.0);
    }

    public HashMap<String, Double> getBaseAndQuoteBalancesFor(Symbol symbol) {
        HashMap<String, Double> balances = new HashMap<>();
        Balance baseBalance = this.getBalance(symbol.getBase());
        Balance quoteBalance = this.getBalance(symbol.getQuote());

        balances.put("base", baseBalance.getFreeBalance());
        balances.put("quote", quoteBalance.getFreeBalance());
        return balances;
    }
}
