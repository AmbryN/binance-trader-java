package com.binance.trader.services;

import java.util.LinkedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.binance.connector.client.exceptions.BinanceClientException;
import com.binance.connector.client.exceptions.BinanceConnectorException;
import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.trader.classes.Ticker;
import com.binance.trader.enums.Symbol;
import com.binance.trader.exceptions.BinanceTraderException;
import com.binance.trader.utils.Deserializer;

public class TickerService {
    SpotClientImpl client;

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    public TickerService(SpotClientImpl client) {
        this.client = client;
    }

    public Ticker getTicker(Symbol symbol) {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", symbol.getPair());

        try {
            String result = client.createMarket().tickerSymbol(parameters);
            Ticker ticker = Deserializer.deserialize(result, Ticker.class);
            return ticker;
        } catch (BinanceConnectorException e) {
            logger.error("fullErrMessage: {}", e.getMessage(), e);
            throw new BinanceTraderException("TickerService->Malformed request: " + e.getMessage());
        } catch (BinanceClientException e) {
            logger.error("fullErrMessage: {} \nerrMessage: {} \nerrCode: {} \nHTTPStatusCode: {}", 
            e.getMessage(), e.getErrMsg(), e.getErrorCode(), e.getHttpStatusCode(), e);
            throw new BinanceTraderException("TickerService->HTTP Request error: " + e.getMessage());
        }
    }        
}