package com.binance.trader.entities;

public class Ticker {
    private String symbol; 
    private double price;

    public Ticker(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public double getPrice() {
        return this.price;
    }

    public String getSymbol() {
        return this.symbol;
    }
}
