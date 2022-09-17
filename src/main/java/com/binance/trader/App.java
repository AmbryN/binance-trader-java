package com.binance.trader;

import java.time.Instant;
import java.util.LinkedHashMap;

import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.trader.entities.AccountInfo;
import com.binance.trader.entities.Balance;
import com.google.gson.Gson;

public class App 
{
    public static void main( String[] args )
    {
        SpotClientImpl client = new SpotClientImpl(PrivateConfig.TESTNET_API_KEY, PrivateConfig.TESTNET_SECRET_KEY, PrivateConfig.TESTNET_URL);
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();

        Long timestamp = Instant.now().toEpochMilli();
        parameters.put("timestamp", timestamp);

        String result = client.createTrade().account(parameters);
        Gson gson = new Gson();
        AccountInfo accountInfo = gson.fromJson(result, AccountInfo.class);

        String symbol = "BNB";
        System.out.println(accountInfo.getBalance(symbol).freeBalance());
    }
}
