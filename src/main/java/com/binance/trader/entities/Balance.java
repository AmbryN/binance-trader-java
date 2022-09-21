package com.binance.trader.entities;

public class Balance {
    String asset;
    double free;
    double locked;

    Balance(String asset, double free, double locked) {
        this.asset = asset;
        this.free = free;
        this.locked = locked;
    }

    public double freeBalance() {
        return this.free;
    }
}
