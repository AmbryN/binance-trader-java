package com.binance.trader.exceptions;

public class BinanceTraderException extends RuntimeException {
    private String message;
    
    public BinanceTraderException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
