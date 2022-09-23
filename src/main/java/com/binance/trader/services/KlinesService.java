package com.binance.trader.services;

import com.binance.connector.client.utils.WebSocketCallback;
import com.binance.connector.client.impl.WebsocketClientImpl;
import com.binance.trader.PrivateConfig;


public class KlinesService {
    private WebsocketClientImpl client;
    private WebSocketCallback onMessageCallback;
    public String klines;

    public KlinesService() {
        this.client = new WebsocketClientImpl(PrivateConfig.TESTNET_URL);
        this.onMessageCallback = new OnMessageCallback(this.klines);
    }

    public void getKlines() {
        this.client.klineStream("BTCUSDT", "1s", onMessageCallback);
    }
}

class OnMessageCallback implements WebSocketCallback {

    String box;

    OnMessageCallback(String box) {
        this.box = box;
    }

    public void onReceive(String message) {
        this.box = message;
    }
    
}
