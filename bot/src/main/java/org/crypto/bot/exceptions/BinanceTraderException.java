package org.crypto.bot.exceptions;

public class BinanceTraderException extends RuntimeException {
    private final String message;
    
    public BinanceTraderException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
