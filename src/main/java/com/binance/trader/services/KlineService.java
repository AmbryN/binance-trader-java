package com.binance.trader.services;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.trader.PrivateConfig;
import com.binance.trader.entities.Kline;
import com.binance.trader.enums.Symbol;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class KlineService {
    SpotClientImpl client;

    public KlineService() {
        this.client =  new SpotClientImpl(PrivateConfig.TESTNET_URL);
    }

    public ArrayList<Kline> fetchKlines(Symbol symbol) {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", symbol.getPair());
        parameters.put("interval", "1s");
        parameters.put("limit", 2);
        String klinesResult = this.client.createMarket().klines(parameters);

        System.out.println(klinesResult);

        Gson gson = new Gson();
        JsonArray array = JsonParser.parseString(klinesResult).getAsJsonArray();
        ArrayList<Kline> klines = new ArrayList<>();

        for (JsonElement item : array) {
            Long openTime = gson.fromJson(item.getAsJsonArray().get(0), Long.class);
            Float openPrice = gson.fromJson(item.getAsJsonArray().get(1), Float.class);
            Float highPrice = gson.fromJson(item.getAsJsonArray().get(2), Float.class);
            Float lowPrice = gson.fromJson(item.getAsJsonArray().get(3), Float.class);
            Float closePrice = gson.fromJson(item.getAsJsonArray().get(4), Float.class);
            Float volume = gson.fromJson(item.getAsJsonArray().get(5), Float.class);
            Long closeTime = gson.fromJson(item.getAsJsonArray().get(6), Long.class);
            Float quoteVolume = gson.fromJson(item.getAsJsonArray().get(7), Float.class);
            int nbOfTrades = gson.fromJson(item.getAsJsonArray().get(8), Integer.class);
            Float takerBuyBaseVolume = gson.fromJson(item.getAsJsonArray().get(9), Float.class);
            Float takerBuyQuoteVolume = gson.fromJson(item.getAsJsonArray().get(10), Float.class);

            klines.add(new Kline(
                openTime,
                openPrice,
                highPrice,
                lowPrice,
                closePrice,
                volume,
                closeTime,
                quoteVolume,
                nbOfTrades,
                takerBuyBaseVolume,
                takerBuyQuoteVolume
            ));                
        }
        return klines;
    }
}
