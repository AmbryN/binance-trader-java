package org.crypto.bot.exceptions;

/**
 * Custom exception for the trading bot
 */
public class BinanceTraderException extends RuntimeException {
    private final String message;
    
    public BinanceTraderException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
