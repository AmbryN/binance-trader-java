package com.binance.trader.enums;

public enum Symbol {
    BTCUSDT("BTCUSDT");

    private String pair;

    Symbol(String pair) {
        this.pair = pair;
    }

    public String getBase() {
        return this.pair.substring(0, 3);
    }

    public String getQuote() {
        return this.pair.substring(3);
    }

    public String getPair() {
        return this.pair;
    }

    public static Symbol toSymbol(String pair) {
        for (Symbol symbol : Symbol.values()) {
            if (symbol.getPair().equals(pair)) {
                return symbol;
            }
        }
        return null;
    }
}
