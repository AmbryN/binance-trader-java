package org.crypto.bot.classes.handlers;

import com.binance.connector.client.exceptions.BinanceClientException;
import com.binance.connector.client.exceptions.BinanceConnectorException;
import com.binance.connector.client.exceptions.BinanceServerException;

/**
 * Represents an action (e.g., function call, which returns a result) which can fail.
 * @param <T> the action, which can fail
 */
@FunctionalInterface
public interface FallibleAction<T> {
    T result() throws BinanceConnectorException, BinanceServerException, BinanceClientException;
}
