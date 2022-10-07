package com.binance.trader.services;

import java.time.Instant;
import java.util.LinkedHashMap;

import ch.qos.logback.classic.Logger;
import com.binance.trader.classes.singleton.Logging;

import com.binance.connector.client.exceptions.BinanceClientException;
import com.binance.connector.client.exceptions.BinanceConnectorException;
import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.trader.classes.data.AccountInfo;
import com.binance.trader.exceptions.BinanceTraderException;
import com.binance.trader.utils.Deserializer;

public class AccountInfoService {
    SpotClientImpl client;

    private static final Logger logger = Logging.getInstance();

    public AccountInfoService(SpotClientImpl client) {
        this.client = client;
    }

    public AccountInfo getAccountInfo() {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        Long timestamp = Instant.now().toEpochMilli();
        parameters.put("timestamp", timestamp);

        try {
            String result = client.createTrade().account(parameters);
            AccountInfo accountInfo = Deserializer.deserialize(result, AccountInfo.class);
            return accountInfo;
        } catch (BinanceConnectorException e) {
            logger.error("fullErrMessage: {}", e.getMessage(), e);
            throw new BinanceTraderException("AccountInfoService->Malformed request: " + e.getMessage());
        } catch (BinanceClientException e) {
            logger.error("fullErrMessage: {} \nerrMessage: {} \nerrCode: {} \nHTTPStatusCode: {}", 
            e.getMessage(), e.getErrMsg(), e.getErrorCode(), e.getHttpStatusCode(), e);
            throw new BinanceTraderException("AccountInfoService->HTTP request error: " + e.getMessage());
        }
    }
}
