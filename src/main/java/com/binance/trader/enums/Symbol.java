package com.binance.trader.enums;

public enum Symbol {
    BTCUSDT(0, "BTC", "USDT");

    String base;
    String quote;
    int position;

    Symbol(int position) {
        this.position = position;
    }

    Symbol(int position, String trade, String quote) {
        this.position = position;
        this.base = trade;
        this.quote = quote;
    }

    public String getBase() {
        return this.base;
    }

    public int getPosition() {
        return this.position;
    }

    public static Symbol getSymbol(int userSymbol) {
        for (Symbol symbol : Symbol.values()) {
            if (userSymbol == symbol.position) {
                return symbol;
            }
        }
        return null;
    };
}
