package com.binance.trader.enums;

public enum Symbol {
    BTCUSDT(Crypto.BTC, Crypto.USDT, 0.00001, 10, 100000, 100),
    BTCBUSD(Crypto.BTC, Crypto.BUSD, 0.00001, 10, 100000, 100),
    ETHUSDT(Crypto.ETH, Crypto.USDT, 0.0001, 10, 10000, 100),
    ETHBUSD(Crypto.ETH, Crypto.BUSD, 0.0001, 10, 10000, 100),
    LUNAUSDT(Crypto.LUNA, Crypto.USDT,0.01 , 10, 100, 100000),
    LUNABUSD(Crypto.LUNA, Crypto.BUSD, 0.01 , 10, 100, 100000);

    private final Crypto base;
    private final Crypto quote;

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
     * Given as an int (e.g. 100000 means 0.00001)
     */
    public final int MIN_BASE_MOVEMENT;
    /**
     * Defines the minimum increment for the amount of a trade using this quote crypto
     * Given as an int (e.g. 100 means 0.01)
     */
    public final int MIN_QUOTE_MOVEMENT;

    Symbol(Crypto base, Crypto quote, double minBaseTransaction, double minQuoteTransaction, int minBaseMovement, int minQuoteMovement) {
        this.base = base;
        this.quote = quote;
        this.MIN_BASE_TRANSACTION = minBaseTransaction;
        this.MIN_QUOTE_TRANSACTION = minQuoteTransaction;
        this.MIN_BASE_MOVEMENT = minBaseMovement;
        this.MIN_QUOTE_MOVEMENT = minQuoteMovement;
    }

    public Crypto getBase() {
        return this.base;
    }

    public Crypto getQuote() {
        return this.quote;
    }

    public String getPair() {
        return this.base.toString() + this.quote.toString();
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
