package org.crypto.bot.services;

import com.binance.connector.client.exceptions.BinanceClientException;
import com.binance.connector.client.exceptions.BinanceConnectorException;
import com.binance.connector.client.exceptions.BinanceServerException;
import com.binance.connector.client.impl.SpotClientImpl;
import org.crypto.bot.classes.data.AccountInfo;
import org.crypto.bot.utils.Deserializer;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.concurrent.CompletableFuture;

/**
 * Service used to query Binance for the user's account information.
 */
public class AccountInfoService {
    SpotClientImpl client;

    /**
     * Creates a new instance of the AccountInfoService
     * @param client the connection client to the exchange
     */
    public AccountInfoService(SpotClientImpl client) {
        this.client = client;
    }

    /**
     * Queries Binance's API to get the Account Information of the user
     */
    public CompletableFuture<AccountInfo> getAccountInfo() throws BinanceConnectorException, BinanceClientException, BinanceServerException {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        Long timestamp = Instant.now().toEpochMilli();
        parameters.put("timestamp", timestamp);

        return CompletableFuture
                .supplyAsync(() -> client.createTrade().account(parameters))
                .thenApply(result -> Deserializer.deserialize(result, AccountInfo.class));
    }
}
