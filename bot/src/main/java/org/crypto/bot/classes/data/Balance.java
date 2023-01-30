package org.crypto.bot.classes.data;

import org.crypto.bot.enums.Crypto;

public class Balance {
    private final Crypto asset;
    private final double free;
    private final double locked;

    public Balance(Crypto asset, double free, double locked) {
        this.asset = asset;
        this.free = free;
        this.locked = locked;
    }

    public double getFreeBalance() {
        return this.free;
    }

    public Crypto getAsset() {
        return this.asset;
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
