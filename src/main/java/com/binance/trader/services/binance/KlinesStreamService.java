package com.binance.trader.services.binance;

import com.binance.connector.client.impl.WebsocketClientImpl;
import com.binance.trader.classes.data.KlinesStream;
import com.binance.trader.enums.Symbol;
import com.binance.trader.utils.Deserializer;

public class KlinesStreamService {
    private final WebsocketClientImpl client;
    private static final String TESTNET_URL = "https://testnet.binance.vision";
    private static final String BINANCE_URL = "https://api.binance.com";

    public KlinesStreamService() {
        this.client = new WebsocketClientImpl(TESTNET_URL);
    }

    public int initializeKlinesStream(Symbol symbol) {
        return this.client.klineStream(symbol.getPair(), "1s", (message) -> {
            KlinesStream klines = Deserializer.deserialize(message, KlinesStream.class);
            System.out.println(klines);
        });
    }

    public void closeKlinesStream(int streamID) {
        this.client.closeConnection(streamID);
    }
}
