package org.crypto.bot.services;

import java.util.LinkedHashMap;

import com.binance.connector.client.exceptions.BinanceServerException;

import com.binance.connector.client.exceptions.BinanceClientException;
import com.binance.connector.client.exceptions.BinanceConnectorException;
import com.binance.connector.client.impl.SpotClientImpl;
import org.crypto.bot.classes.data.Ticker;
import org.crypto.bot.enums.Symbol;
import org.crypto.bot.utils.Deserializer;

/**
 * Used to retrieve the current price of a crypto pair
 */
public class TickerService {
    SpotClientImpl client;

    public TickerService(SpotClientImpl client) {
        this.client = client;
    }

    /**
     * Queries Binance's API to get the current Ticker price for a symbol.
     * @param symbol symbol for which to retrieve the price
     */
    public Ticker getTicker(Symbol symbol) throws BinanceConnectorException, BinanceClientException, BinanceServerException {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", symbol.toString());

        String result;
        result = client.createMarket().tickerSymbol(parameters);
        return Deserializer.deserialize(result, Ticker.class);
    }
}
