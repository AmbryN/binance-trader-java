package com.binance.trader.enums;

public enum Symbol {
    BTCUSDT("BTCUSDT"),
    None;

    String symbol;

    Symbol() {}

    Symbol(String symbol){
        this.symbol = symbol;
    }

    private String getSymbolAsString() {
        return this.symbol;
    }

    public String getBuySymbol() {
        return this.symbol.substring(0, 3);
    }

    public static boolean isValid(String userSymbol) {
        for (Symbol symbol : Symbol.values()) {
            if (userSymbol.equals(symbol.getSymbolAsString())) {
                return true;
            }
        }
        return false;
    }

    public static Symbol getSymbol(String userSymbol) {
        for (Symbol symbol : Symbol.values()) {
            if (userSymbol.equals(symbol.getSymbolAsString())) {
                return symbol;
            }
        }
        return Symbol.None;
    };
}
