package com.binance.trader.entities;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.trader.PrivateConfig;
import com.binance.trader.enums.OrderSide;
import com.binance.trader.enums.OrderType;
import com.binance.trader.enums.Symbol;
import com.binance.trader.enums.TimeInForce;
import com.binance.trader.intefaces.Strategy;
import com.binance.trader.services.KlineService;
import com.binance.trader.services.OrderService;
import com.binance.trader.utils.Calculus;
import com.binance.trader.utils.Deserializer;
import com.binance.trader.utils.Logger;

public class MovingAvgStrategy implements Strategy {
    private SpotClientImpl client;
    private String name = "MovingAvg";
    private String period;
    private int nbOfPeriods;
    private final static double MIN_BASE_TRANSACTION = 0.00001;
    private final static double MIN_QUOTE_TRANSACTION = 10;

    public MovingAvgStrategy(String period, int nbOfPeriods) {
        this.client = new SpotClientImpl(PrivateConfig.TESTNET_API_KEY, PrivateConfig.TESTNET_SECRET_KEY, PrivateConfig.TESTNET_URL);
        this.period = period;
        this.nbOfPeriods = nbOfPeriods;
    }

    @Override
    public void execute(Symbol symbol) {
        
        OrderService orderService = new OrderService();

        while(true) {
            boolean shouldBuy = false;
            boolean shouldSell = false;

            ArrayList<Balance> balances = this.getBalances(symbol);
            double baseBalance = balances.get(0).getFreeBalance();
            double quoteBalance = balances.get(1).getFreeBalance();
    
            double tickerPrice = this.getTicker(symbol);
            double movingAvg = this.calculateMovingAvg(symbol, this.period, this.nbOfPeriods);
            
            Logger.print("Base balance: " + baseBalance + " / Quote balance: " + quoteBalance + " / Ticker " + tickerPrice + " / MAvg " + calculateMovingAvg(symbol, this.period, this.nbOfPeriods) + " / Should buy " + shouldBuy + " / Should sell " + shouldSell);

            if (tickerPrice > movingAvg && quoteBalance > MIN_QUOTE_TRANSACTION) {
                shouldBuy = true;
                OrderBuildImpl orderBuilder = new OrderBuildImpl();
                orderBuilder.setSymbol(symbol);
                orderBuilder.setSide(OrderSide.BUY);
                orderBuilder.setType(OrderType.LIMIT);
                orderBuilder.setTimeInForce(TimeInForce.IOC);
                orderBuilder.setPrice(tickerPrice);
                orderBuilder.setQuantity(Math.round(quoteBalance * 0.99 / tickerPrice * 1000000.) / 1000000.);
                Order order = orderBuilder.getResult();

                orderService.sendOrder(order);
            } else {
                shouldBuy = false;
            }

            if (tickerPrice < movingAvg && baseBalance > MIN_BASE_TRANSACTION) {
                shouldSell = true;
                OrderBuildImpl orderBuilder = new OrderBuildImpl();
                orderBuilder.setSymbol(symbol);
                orderBuilder.setSide(OrderSide.SELL);
                orderBuilder.setType(OrderType.LIMIT);
                orderBuilder.setTimeInForce(TimeInForce.IOC);
                orderBuilder.setPrice(tickerPrice);
                orderBuilder.setQuantity(baseBalance);
                Order order = orderBuilder.getResult();

                orderService.sendOrder(order);
            } else {
                shouldSell = false;
            }
            
            // try {
            //     Thread.sleep(5000);
            // } catch (InterruptedException e) {
            //     e.printStackTrace();
            // }
        }
    }

    private double calculateMovingAvg(Symbol symbol, String period, int nbOfPeriods) {        
        KlineService klineService = new KlineService();
        ArrayList<Kline> klines = klineService.fetchKlines(symbol, period, nbOfPeriods);
        ArrayList<Double> prices = new ArrayList<Double>();

        klines.forEach((kline) -> prices.add(kline.getClosePrice()));
        
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
        balances.add(accountInfo.getBalance(symbol.getQuote()));
        return balances;
    }


    private double getTicker(Symbol symbol) {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", symbol.getPair());

        String result = client.createMarket().tickerSymbol(parameters);
        Ticker ticker = Deserializer.deserialize(result, Ticker.class);
        return ticker.getPrice();
    }

    public String toString() {
        return this.name;
    }
}
