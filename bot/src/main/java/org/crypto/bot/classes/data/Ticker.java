package org.crypto.bot.classes.data;

import org.crypto.bot.enums.Symbol;

/**
 * Represents the current price of a crypto pair on Binance
 */
public class Ticker {
    private final Symbol symbol;
    private final double price;

    public Ticker(Symbol symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public double getPrice() {
        return this.price;
    }

    public Symbol getSymbol() {
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
