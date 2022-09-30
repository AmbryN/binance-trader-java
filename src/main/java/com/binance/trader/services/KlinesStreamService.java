package com.binance.trader.services;

import com.binance.connector.client.impl.WebsocketClientImpl;
//import com.binance.trader.PrivateConfig;
import com.binance.trader.classes.KlinesStream;
import com.binance.trader.enums.Symbol;
import com.binance.trader.utils.Deserializer;

public class KlinesStreamService {
    private WebsocketClientImpl client;

    public KlinesStreamService() {
        this.client = new WebsocketClientImpl(System.getenv("TESTNET_URL"));
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
