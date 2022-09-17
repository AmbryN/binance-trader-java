package com.binance.trader.entities;

public class Balance {
    String asset;
    String free;
    String locked;

    Balance() {}

    Balance(String asset, String free, String locked) {
        this.asset = asset;
        this.free = free;
        this.locked = locked;
    }

    public String freeBalance() {
        return this.free;
    }
}
