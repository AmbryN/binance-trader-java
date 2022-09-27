package com.binance.trader.entities;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.trader.PrivateConfig;
import com.binance.trader.enums.Symbol;
import com.binance.trader.intefaces.Strategy;
import com.binance.trader.services.KlineService;
import com.binance.trader.utils.Calculus;
import com.binance.trader.utils.Deserializer;
import com.binance.trader.utils.Logger;

public class MovingAvgStrategy implements Strategy {
    private SpotClientImpl client;
    private String name = "MovingAvg";
    private String period;
    private int nbOfPeriods;

    public MovingAvgStrategy(String period, int nbOfPeriods) {
        this.client = new SpotClientImpl(PrivateConfig.TESTNET_API_KEY, PrivateConfig.TESTNET_SECRET_KEY, PrivateConfig.TESTNET_URL);
        this.period = period;
        this.nbOfPeriods = nbOfPeriods;
    }

    @Override
    public void execute(Symbol symbol) {
        ArrayList<Balance> balances = this.getBalances(symbol);

        double movingAvg = this.calculateMovingAvg(symbol, this.period, this.nbOfPeriods);

        double tickerPrice = this.getTicker(symbol);

        boolean shouldBuy = false;
        boolean shouldSell = false;

        if (tickerPrice > movingAvg) {
            shouldBuy = true;
        } else {
            shouldBuy = false;
        }

        if (tickerPrice < movingAvg) {
            shouldSell = true;
        } else {
            shouldSell = false;
        }
        Logger.print("Base balance: " + balances.get(0).free + " / Quote balance: " + balances.get(1).free + " / Ticker " + tickerPrice + " / MAvg " + calculateMovingAvg(symbol, this.period, this.nbOfPeriods) + " / Should buy " + shouldBuy + " / Should sell " + shouldSell);
    }

    private double calculateMovingAvg(Symbol symbol, String period, int nbOfPeriods) {        
        KlineService klineService = new KlineService();
        ArrayList<Kline> klines = klineService.fetchKlines(symbol, period, nbOfPeriods);
        ArrayList<Double> prices = new ArrayList<Double>();

        klines.forEach((kline) -> prices.add(kline.closePrice));
        
        return Calculus.calculateAvg(prices);
    }

    private ArrayList<Balance> getBalances(Symbol symbol) {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        Long timestamp = Instant.now().toEpochMilli();
        parameters.put("timestamp", timestamp);

        String result = client.createTrade().account(parameters);
        AccountInfo accountInfo = Deserializer.deserialize(result, AccountInfo.class);
        
        ArrayList<Balance> balances = new ArrayList<>();
        balances.add(accountInfo.getBalance(symbol.getBase()));
        balances.add(accountInfo.getBalance(symbol.getPair()));
        return balances;
    }


    private double getTicker(Symbol symbol) {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", symbol.getPair());

        String result = client.createMarket().tickerSymbol(parameters);
        Ticker ticker = Deserializer.deserialize(result, Ticker.class);
        return ticker.price;
    }

    public String toString() {
        return this.name;
    }
}
