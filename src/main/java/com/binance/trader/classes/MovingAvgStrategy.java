package com.binance.trader.classes;

import java.util.ArrayList;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

import com.binance.connector.client.impl.SpotClientImpl;
//import com.binance.trader.PrivateConfig;
import com.binance.trader.enums.Symbol;
import com.binance.trader.intefaces.Strategy;
import com.binance.trader.services.AccountInfoService;
import com.binance.trader.services.KlineService;
import com.binance.trader.services.OrderService;
import com.binance.trader.services.TickerService;
import com.binance.trader.utils.Calculus;

public class MovingAvgStrategy implements Strategy {
    private final SpotClientImpl client;
    private final String period;
    private final int nbOfPeriods;

    //private static final Logger logger = LoggerFactory.getLogger(MovingAvgStrategy.class);

    public MovingAvgStrategy(String period, int nbOfPeriods) {
        this.client = new SpotClientImpl(System.getenv("TESTNET_API_KEY"), System.getenv("TESTNET_SECRET_KEY"), System.getenv("TESTNET_URL"));
        this.period = period;
        this.nbOfPeriods = nbOfPeriods;
    }

    @Override
    public void execute(Symbol symbol) {
        
        while(true) {
            AccountInfoService accountInfoService = new AccountInfoService(client);
            AccountInfo accountInfo = accountInfoService.getAccountInfo();
            double baseBalance = accountInfo.getBalance(symbol.getBase()).getFreeBalance();
            double quoteBalance = accountInfo.getBalance(symbol.getQuote()).getFreeBalance();

            TickerService tickerService = new TickerService(client);
            double tickerPrice = tickerService.getTicker(symbol).getPrice();
            double movingAvg = this.calculateMovingAvg(symbol, this.period, this.nbOfPeriods);
            
            System.out.println("Base balance: " + baseBalance + " / Quote balance: " + quoteBalance + " / Ticker " + tickerPrice +
                                 " / MAvg " + calculateMovingAvg(symbol, this.period, this.nbOfPeriods));

            OrderService orderService = new OrderService();
            if (tickerPrice > movingAvg && quoteBalance > symbol.MIN_QUOTE_TRANSACTION) {
                orderService.buy(symbol, tickerPrice, quoteBalance);
            } else if (tickerPrice < movingAvg && baseBalance > symbol.MIN_BASE_TRANSACTION) {
                orderService.sell(symbol, tickerPrice, baseBalance);
            }
        }
    }

    private double calculateMovingAvg(Symbol symbol, String period, int nbOfPeriods) {
        KlineService klineService = new KlineService(client);

        ArrayList<Kline> klines = klineService.fetchKlines(symbol, period, nbOfPeriods);
        ArrayList<Double> prices = new ArrayList<>();

        klines.forEach((kline) -> prices.add(kline.getClosePrice()));
        
        return Calculus.calculateAvg(prices);
    }

    @Override
    public String toString() {
        return "MovingAvg";
    }
}
