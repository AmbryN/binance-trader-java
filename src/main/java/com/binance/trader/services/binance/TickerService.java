package com.binance.trader.services.binance;

import java.util.LinkedHashMap;

import ch.qos.logback.classic.Logger;
import com.binance.connector.client.exceptions.BinanceServerException;
import com.binance.trader.utils.Logging;

import com.binance.connector.client.exceptions.BinanceClientException;
import com.binance.connector.client.exceptions.BinanceConnectorException;
import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.trader.classes.data.Ticker;
import com.binance.trader.enums.Symbol;
import com.binance.trader.exceptions.BinanceTraderException;
import com.binance.trader.utils.Deserializer;

public class TickerService {
    SpotClientImpl client;

    private static final Logger logger = Logging.getInstance();

    public TickerService(SpotClientImpl client) {
        this.client = client;
    }

    /**
     * Queries Binance's API to get the current Ticker price for symbol
     * @param symbol crypto pair for which the Ticker is queried
     * @throws BinanceConnectorException lets it through to be caught by the
     * FallibleBinanceAction interface to log it and retry connection
     * @throws BinanceClientException  lets it through to be caught by the
     * FallibleBinanceAction interface to log it and let the program crash
     * @throws BinanceServerException lets it through to be caught by the
     * FallibleBinanceAction interface to log it and retry connection
     * @return Ticker of the symbol
     */
    public Ticker getTicker(Symbol symbol) {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", symbol.getPair());

        String result;
        result = client.createMarket().tickerSymbol(parameters);
        return Deserializer.deserialize(result, Ticker.class);
    }
}
