package org.crypto.bot.exceptions;

/**
 * Custom exception for the trading bot
 */
public class BinanceTraderException extends RuntimeException {

    public BinanceTraderException(String message) {
          super(message);
    }

    public BinanceTraderException(String message, Throwable t) {
        super(message, t);
    }
}
