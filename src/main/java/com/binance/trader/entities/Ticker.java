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

        Ticker ticker = (Ticker) o;
        return this.symbol.equals(ticker.symbol) && this.price == ticker.price;
    }
}
