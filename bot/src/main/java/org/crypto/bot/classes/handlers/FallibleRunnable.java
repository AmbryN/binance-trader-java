package org.crypto.bot.classes.handlers;

import com.binance.connector.client.exceptions.BinanceClientException;
import com.binance.connector.client.exceptions.BinanceConnectorException;
import com.binance.connector.client.exceptions.BinanceServerException;

/**
 * Represents a runnable (e.g., procedure call with no result) which can fail.
 */
@FunctionalInterface
public interface FallibleRunnable {
    void run() throws BinanceConnectorException, BinanceServerException, BinanceClientException;
}
