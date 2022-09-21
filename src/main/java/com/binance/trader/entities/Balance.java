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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }

        Balance balance = (Balance) o;
        return this.asset.equals(balance.asset) && this.free == balance.free && this.locked == balance.locked;
    }
}
