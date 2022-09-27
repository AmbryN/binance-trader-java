package com.binance.trader.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.connector.client.impl.spot.Market;
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
        Market market = this.client.createMarket();
        String klinesResult = market.klines(parameters);

        Gson gson = new Gson();
        JsonArray array = JsonParser.parseString(klinesResult).getAsJsonArray();
        ArrayList<Kline> klines = new ArrayList<>();

        for (JsonElement item : array) {
            Long openTime = gson.fromJson(item.getAsJsonArray().get(0), Long.class);
            BigDecimal openPrice = gson.fromJson(item.getAsJsonArray().get(1), BigDecimal.class);
            BigDecimal highPrice = gson.fromJson(item.getAsJsonArray().get(2), BigDecimal.class);
            BigDecimal lowPrice = gson.fromJson(item.getAsJsonArray().get(3), BigDecimal.class);
            BigDecimal closePrice = gson.fromJson(item.getAsJsonArray().get(4), BigDecimal.class);
            BigDecimal volume = gson.fromJson(item.getAsJsonArray().get(5), BigDecimal.class);
            Long closeTime = gson.fromJson(item.getAsJsonArray().get(6), Long.class);
            BigDecimal quoteVolume = gson.fromJson(item.getAsJsonArray().get(7), BigDecimal.class);
            int nbOfTrades = gson.fromJson(item.getAsJsonArray().get(8), Integer.class);
            BigDecimal takerBuyBaseVolume = gson.fromJson(item.getAsJsonArray().get(9), BigDecimal.class);
            BigDecimal takerBuyQuoteVolume = gson.fromJson(item.getAsJsonArray().get(10), BigDecimal.class);

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
