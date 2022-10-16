package com.binance.trader.services;

import java.util.LinkedHashMap;

import com.binance.connector.client.exceptions.BinanceServerException;

import com.binance.connector.client.exceptions.BinanceClientException;
import com.binance.connector.client.exceptions.BinanceConnectorException;
import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.trader.classes.data.Ticker;
import com.binance.trader.enums.Symbol;
import com.binance.trader.utils.Deserializer;

public class TickerService {
    SpotClientImpl client;

    public TickerService(SpotClientImpl client) {
        this.client = client;
    }

    /**
     * Queries Binance's API to get the current Ticker price for symbol
     */
    public Ticker getTicker(Symbol symbol) throws BinanceConnectorException, BinanceClientException, BinanceServerException {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", symbol.getPair());

        String result;
        result = client.createMarket().tickerSymbol(parameters);
        return Deserializer.deserialize(result, Ticker.class);
    }
}
