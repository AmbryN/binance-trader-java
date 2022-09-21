package com.binance.trader.entities;

import java.time.Instant;
import java.util.LinkedHashMap;

import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.trader.PrivateConfig;
import com.binance.trader.enums.Symbol;
import com.binance.trader.intefaces.Strategy;
import com.binance.trader.utils.Deserializer;
import com.binance.trader.utils.Logger;

public class MovingAvgStrategy implements Strategy {
    private SpotClientImpl client;
    String name = "MovingAvg";

    public MovingAvgStrategy() {
        this.client = new SpotClientImpl(PrivateConfig.TESTNET_API_KEY, PrivateConfig.TESTNET_SECRET_KEY, PrivateConfig.TESTNET_URL);
    }

    @Override
    public void execute(Symbol symbol) {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        Long timestamp = Instant.now().toEpochMilli();
        parameters.put("timestamp", timestamp);
    
        String result = client.createTrade().account(parameters);
        AccountInfo accountInfo = Deserializer.deserialize(result, AccountInfo.class);
    
        String cryptoBuy = symbol.getTraded();
        double freeBalance = accountInfo.getBalance(cryptoBuy).freeBalance();
        Logger.print(freeBalance);
    }

    private void getTicker() {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", "BTCUSDT");

        String result = client.createMarket().tickerSymbol(parameters);
        Ticker ticker = Deserializer.deserialize(result, Ticker.class);
        Logger.print(ticker.getPrice());
    }

    public String toString() {
        return this.name;
    }
}
