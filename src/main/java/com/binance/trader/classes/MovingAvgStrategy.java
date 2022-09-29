package com.binance.trader.classes;

import java.util.ArrayList;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.trader.PrivateConfig;
import com.binance.trader.enums.OrderSide;
import com.binance.trader.enums.OrderType;
import com.binance.trader.enums.Symbol;
import com.binance.trader.enums.TimeInForce;
import com.binance.trader.intefaces.Strategy;
import com.binance.trader.services.AccountInfoService;
import com.binance.trader.services.KlineService;
import com.binance.trader.services.OrderService;
import com.binance.trader.services.TickerService;
import com.binance.trader.utils.Calculus;

public class MovingAvgStrategy implements Strategy {
    private SpotClientImpl client;
    private AccountInfoService accountInfoService;
    private TickerService tickerService;
    private OrderService orderService;
    private KlineService klineService;
    private String name = "MovingAvg";
    private String period;
    private int nbOfPeriods;

    //private static final Logger logger = LoggerFactory.getLogger(MovingAvgStrategy.class);

    public MovingAvgStrategy(String period, int nbOfPeriods) {
        this.client = new SpotClientImpl(PrivateConfig.TESTNET_API_KEY, PrivateConfig.TESTNET_SECRET_KEY, PrivateConfig.TESTNET_URL);
        this.accountInfoService = new AccountInfoService(client);
        this.tickerService = new TickerService(client);
        this.orderService = new OrderService();
        this.klineService = new KlineService(client);
        this.period = period;
        this.nbOfPeriods = nbOfPeriods;
    }

    @Override
    public void execute(Symbol symbol) {
        
        while(true) {
            AccountInfo accountInfo = accountInfoService.getAccountInfo();
            double baseBalance = accountInfo.getBalance(symbol.getBase()).getFreeBalance();
            double quoteBalance = accountInfo.getBalance(symbol.getQuote()).getFreeBalance();
    
            double tickerPrice = tickerService.getTicker(symbol).getPrice();
            double movingAvg = this.calculateMovingAvg(symbol, this.period, this.nbOfPeriods);
            
            System.out.println("Base balance: " + baseBalance + " / Quote balance: " + quoteBalance + " / Ticker " + tickerPrice +
                                 " / MAvg " + calculateMovingAvg(symbol, this.period, this.nbOfPeriods));

            if (tickerPrice > movingAvg && quoteBalance > symbol.MIN_QUOTE_TRANSACTION) {
                this.sendBuyOrder(symbol, tickerPrice, quoteBalance);

            } else if (tickerPrice < movingAvg && baseBalance > symbol.MIN_BASE_TRANSACTION) {
                this.sendSellOrder(symbol, tickerPrice, baseBalance);
            }
        }
    }

    private double calculateMovingAvg(Symbol symbol, String period, int nbOfPeriods) {        
        ArrayList<Kline> klines = klineService.fetchKlines(symbol, period, nbOfPeriods);
        ArrayList<Double> prices = new ArrayList<Double>();

        klines.forEach((kline) -> prices.add(kline.getClosePrice()));
        
        return Calculus.calculateAvg(prices);
    }

    private void sendBuyOrder(Symbol symbol, double tickerPrice, double quoteBalance) {
        double quoteQuantity = Math.floor(quoteBalance / tickerPrice / symbol.MIN_QUOTE_MOVEMENT) * symbol.MIN_QUOTE_MOVEMENT;

        OrderBuildImpl orderBuilder = new OrderBuildImpl();
        orderBuilder.reset();
        orderBuilder.setSymbol(symbol);
        orderBuilder.setSide(OrderSide.BUY);
        orderBuilder.setType(OrderType.LIMIT);
        orderBuilder.setTimeInForce(TimeInForce.IOC);
        orderBuilder.setPrice(tickerPrice);
        orderBuilder.setQuantity(quoteQuantity);
        Order order = orderBuilder.getResult();

        orderService.sendOrder(order);
    }

    private void sendSellOrder(Symbol symbol, double tickerPrice, double baseBalance) {
        double baseQuantity = Math.floor(baseBalance / symbol.MIN_BASE_MOVEMENT) * symbol.MIN_BASE_MOVEMENT;

        OrderBuildImpl orderBuilder = new OrderBuildImpl();
        orderBuilder.reset();
        orderBuilder.setSymbol(symbol);
        orderBuilder.setSide(OrderSide.SELL);
        orderBuilder.setType(OrderType.LIMIT);
        orderBuilder.setTimeInForce(TimeInForce.IOC);
        orderBuilder.setPrice(tickerPrice);
        orderBuilder.setQuantity(baseQuantity);
        Order order = orderBuilder.getResult();

        orderService.sendOrder(order);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
