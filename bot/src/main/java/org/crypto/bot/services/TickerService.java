package org.crypto.bot.services;

import java.util.LinkedHashMap;
import java.util.concurrent.CompletableFuture;

import com.binance.connector.client.exceptions.BinanceServerException;

import com.binance.connector.client.exceptions.BinanceClientException;
import com.binance.connector.client.exceptions.BinanceConnectorException;
import com.binance.connector.client.impl.SpotClientImpl;
import org.crypto.bot.classes.data.Ticker;
import org.crypto.bot.enums.Symbol;
import org.crypto.bot.utils.Deserializer;

public class TickerService {
    SpotClientImpl client;

    public TickerService(SpotClientImpl client) {
        this.client = client;
    }

    /**
     * Queries Binance's API to get the current Ticker price for symbol
     */
    public CompletableFuture<Ticker> getTicker(Symbol symbol) throws BinanceConnectorException, BinanceClientException, BinanceServerException {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", symbol.toString());

        return CompletableFuture.supplyAsync(() -> client.createMarket().tickerSymbol(parameters))
                .thenApply((result) -> Deserializer.deserialize(result, Ticker.class));
    }
}
