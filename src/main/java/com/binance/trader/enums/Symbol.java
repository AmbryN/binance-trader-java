package com.binance.trader.enums;

public enum Symbol {
    BTCUSDT("BTCUSDT", 0.00001, 10, 0.00001, 0.01),
    ETHUSDT("ETHUSDT", 0.0001, 10, 0.0001, 0.01);

    private final String pair;

    /**
     * Defines the minimum amount for a trade using this base crypto
     */
    public final double MIN_BASE_TRANSACTION;
    /**
     * Defines the minimum amount for a trade using this quote crypto
     */
    public final double MIN_QUOTE_TRANSACTION;
    /**
     * Defines the minimum increment for the amount of a trade using this base crypto
     */
    public final double MIN_BASE_MOVEMENT;
    /**
     * Defines the minimum increment for the amount of a trade using this quote crypto
     */
    public final double MIN_QUOTE_MOVEMENT;

    Symbol(String pair, double minBaseTransaction, double minQuoteTransaction, double minBaseMouvement, double minQuoteMouvement) {
        this.pair = pair;
        this.MIN_BASE_TRANSACTION = minBaseTransaction;
        this.MIN_QUOTE_TRANSACTION = minQuoteTransaction;
        this.MIN_BASE_MOVEMENT = minBaseMouvement;
        this.MIN_QUOTE_MOVEMENT = minQuoteMouvement;
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
