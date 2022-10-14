package com.binance.trader.services.binance;

import java.time.Instant;
import java.util.LinkedHashMap;

import ch.qos.logback.classic.Logger;
import com.binance.connector.client.exceptions.BinanceServerException;
import com.binance.trader.utils.Logging;

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

    /**
     * Queries Binance's API to get the Account Info of the user
     * @throws BinanceConnectorException lets it through to be caught by the
     * FallibleBinanceAction interface to log it and retry connection
     * @throws BinanceClientException  lets it through to be caught by the
     * FallibleBinanceAction interface to log it and let the program crash
     * @throws BinanceServerException lets it through to be caught by the
     * FallibleBinanceAction interface to log it and retry connection
     * @return AccountInfo of the user
     */
    public AccountInfo getAccountInfo() {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        Long timestamp = Instant.now().toEpochMilli();
        parameters.put("timestamp", timestamp);

        String result;
        result = client.createTrade().account(parameters);
        return Deserializer.deserialize(result, AccountInfo.class);
    }
}
