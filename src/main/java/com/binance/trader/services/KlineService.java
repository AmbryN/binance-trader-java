package com.binance.trader.services;

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

    public ArrayList<Kline> fetchKlines(Symbol symbol, String period, int nbOfPeriods) {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", symbol.getPair());
        parameters.put("interval", period);
        parameters.put("limit", nbOfPeriods);
        Market market = this.client.createMarket();
        String klinesResult = market.klines(parameters);

        Gson gson = new Gson();
        JsonArray array = JsonParser.parseString(klinesResult).getAsJsonArray();
        ArrayList<Kline> klines = new ArrayList<>();

        for (JsonElement item : array) {
            Long openTime = gson.fromJson(item.getAsJsonArray().get(0), Long.class);
            Double openPrice = gson.fromJson(item.getAsJsonArray().get(1), Double.class);
            Double highPrice = gson.fromJson(item.getAsJsonArray().get(2), Double.class);
            Double lowPrice = gson.fromJson(item.getAsJsonArray().get(3), Double.class);
            Double closePrice = gson.fromJson(item.getAsJsonArray().get(4), Double.class);
            Double volume = gson.fromJson(item.getAsJsonArray().get(5), Double.class);
            Long closeTime = gson.fromJson(item.getAsJsonArray().get(6), Long.class);
            Double quoteVolume = gson.fromJson(item.getAsJsonArray().get(7), Double.class);
            int nbOfTrades = gson.fromJson(item.getAsJsonArray().get(8), Integer.class);
            Double takerBuyBaseVolume = gson.fromJson(item.getAsJsonArray().get(9), Double.class);
            Double takerBuyQuoteVolume = gson.fromJson(item.getAsJsonArray().get(10), Double.class);

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
