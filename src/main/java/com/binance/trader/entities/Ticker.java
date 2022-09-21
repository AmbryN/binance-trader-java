package com.binance.trader.entities;

public class Ticker {
    String symbol;
    double price;

    public Ticker(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public double getPrice() {
        return this.price;
    }
}
