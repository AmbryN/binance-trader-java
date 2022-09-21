package com.binance.trader;

import java.time.Instant;
import java.util.LinkedHashMap;

import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.trader.entities.AccountInfo;
import com.binance.trader.entities.Ticker;
import com.binance.trader.enums.Symbol;
import com.google.gson.Gson;

public class Trader {

    private SpotClientImpl client;
    
        
    public Trader() {
        this.client = new SpotClientImpl(PrivateConfig.TESTNET_API_KEY, PrivateConfig.TESTNET_SECRET_KEY, PrivateConfig.TESTNET_URL);
    }

    public void trade(Symbol symbol) {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        Long timestamp = Instant.now().toEpochMilli();
        parameters.put("timestamp", timestamp);
    
        String result = client.createTrade().account(parameters);
        AccountInfo accountInfo = deserialize(result, AccountInfo.class);
    
        String cryptoBuy = symbol.getBuySymbol();
        double freeBalance = accountInfo.getBalance(cryptoBuy).freeBalance();
        print(freeBalance);
    }

    public void getTicker() {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", "BTCUSDT");

        String result = client.createMarket().tickerSymbol(parameters);
        Ticker ticker = deserialize(result, Ticker.class);
        print(ticker.getPrice());
    }

    private <T> T deserialize(String json, Class<T> objectClass) {
        Gson gson = new Gson();
        T object = gson.fromJson(json, objectClass);
        return object;
    }

    private <T> void print(T text) {
        System.out.println(text);
    }
    
}
