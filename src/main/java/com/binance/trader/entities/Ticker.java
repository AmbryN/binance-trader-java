package com.binance.trader.entities;

public class Ticker {
    String symbol;
    Float price;

    Ticker() {}

    public Float getPrice() {
        return this.price;
    }
}
