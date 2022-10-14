package com.binance.trader.interfaces;

import com.binance.connector.client.exceptions.BinanceClientException;
import com.binance.connector.client.exceptions.BinanceConnectorException;
import com.binance.connector.client.exceptions.BinanceServerException;

@FunctionalInterface
public interface FallibleAction<T> {
    T result() throws BinanceConnectorException, BinanceServerException, BinanceClientException;
}
