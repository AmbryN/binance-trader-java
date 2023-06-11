package org.crypto.bot.services;

import com.binance.connector.client.impl.SpotClientImpl;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.crypto.bot.classes.data.Kline;
import org.crypto.bot.enums.Symbol;

import java.util.LinkedHashMap;
import java.util.concurrent.CompletableFuture;

/**
 * Service used to retrieve the kline (candle) data
 */
public class KlineService {
    private final SpotClientImpl client;

    public KlineService(SpotClientImpl client) {
        this.client =  client;
    }

    /**
     * Fetches the kline data
     * @param symbol symbol for which we want to fetch the data
     * @param period time period of the data
     * @param nbOfPeriods amount of periods needed
     * @return A future of the array of kline data
     */

    public CompletableFuture<Kline[]> fetchKlines(Symbol symbol, String period, int nbOfPeriods) {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", symbol.toString());
        parameters.put("interval", period);
        parameters.put("limit", nbOfPeriods);

        Gson gson = new Gson();

        return CompletableFuture.supplyAsync(() -> this.client.createMarket().klines(parameters))
                .thenApply(JsonParser::parseString)
                .thenApply(JsonElement::getAsJsonArray)
                .thenApply(jsonElements -> jsonElements.asList().stream().map(item -> {
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
                    return new Kline(
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
                    );
                }).toList().toArray(Kline[]::new));
    }
}
