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
    private String name = "MovingAvg";
    private String timePeriod;
    private String periodNb;

    public MovingAvgStrategy(String timePeriod, String periodNb) {
        this.client = new SpotClientImpl(PrivateConfig.TESTNET_API_KEY, PrivateConfig.TESTNET_SECRET_KEY, PrivateConfig.TESTNET_URL);
        this.timePeriod = timePeriod;
        this.periodNb = periodNb;
    }

    @Override
    public void execute(Symbol symbol) {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        Long timestamp = Instant.now().toEpochMilli();
        parameters.put("timestamp", timestamp);
    
        String result = client.createTrade().account(parameters);
        AccountInfo accountInfo = Deserializer.deserialize(result, AccountInfo.class);
    
        String cryptoBuy = symbol.getBase();
        double freeBalance = accountInfo.getBalance(cryptoBuy).freeBalance();
        Logger.print(freeBalance);
    }

    public double calculateMovingAvg(Symbol symbol) {
        String result;
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", symbol);
        parameters.put("period", this.timePeriod);
        parameters.put("periodNb", this.periodNb);

        return 0.0;
        // try {
        //     result = this.client.createMarket().klines(parameters);
        // }
        //     (this.symbol, this.period, { limit: this.movingAvgPeriod });
        // } catch (err) {
        //     console.error(`Error: ${err}`);
        //     return;
        // }

        // let data = result.data
        // // Compute the moving average
        // let sum = data.reduce((accum, value) => {
        //     accum += parseFloat(value[4])
        //     return accum
        // }, 0)
        // let movingAvg = floorToDecimals(sum / data.length, 4)

        // console.log(`===== Moving Average: ${movingAvg} ===== `)
        // return movingAvg;
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
