package com.binance.trader.classes;

import java.util.ArrayList;

import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.trader.enums.Period;
import com.binance.trader.enums.Symbol;
import com.binance.trader.intefaces.Strategy;
import com.binance.trader.services.AccountInfoService;
import com.binance.trader.services.KlineService;
import com.binance.trader.services.OrderService;
import com.binance.trader.services.TickerService;
import com.binance.trader.utils.Calculus;

public class MovingAvgStrategy implements Strategy {

    private static final String TESTNET_URL = "https://testnet.binance.vision";
    private static final String BINANCE_URL = "https://api.binance.com";
    private final SpotClientImpl client;
    private Period period;
    private int nbOfPeriods;

    public MovingAvgStrategy() {
        this.client = new SpotClientImpl(System.getenv("TESTNET_API_KEY"), System.getenv("TESTNET_SECRET_KEY"), TESTNET_URL);
        this.nbOfPeriods = -1;
    }

    public void init() {
        while (this.period == null) {
            PeriodListSelector selector = new PeriodListSelector();
            this.period = selector.startSelector();
        }
        while (this.nbOfPeriods == -1) {
            IntSelector selector = new IntSelector();
            this.nbOfPeriods = selector.startSelector();
        }
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
            double movingAvg = this.calculateMovingAvg(symbol);
            
            System.out.println("Base balance: " + baseBalance + " / Quote balance: " + quoteBalance + " / Ticker " + tickerPrice +
                                 " / MAvg " + movingAvg);

            OrderService orderService = new OrderService(this.client);
            if (tickerPrice > movingAvg && quoteBalance > symbol.MIN_QUOTE_TRANSACTION) {
                orderService.buy(symbol, tickerPrice, quoteBalance);
            } else if (tickerPrice < movingAvg && baseBalance > symbol.MIN_BASE_TRANSACTION) {
                orderService.sell(symbol, tickerPrice, baseBalance);
            }
        }
    }

    @Override
    public Period getPeriod() {
        return this.period;
    }

    @Override
    public int getNbOfPeriods() {
        return this.nbOfPeriods;
    }

    private double calculateMovingAvg(Symbol symbol) {
        KlineService klineService = new KlineService(client);

        ArrayList<Kline> klines = klineService.fetchKlines(symbol, this.period.asString(), nbOfPeriods);
        ArrayList<Double> prices = new ArrayList<>();

        klines.forEach((kline) -> prices.add(kline.getClosePrice()));
        
        return Calculus.calculateAvg(prices);
    }

    @Override
    public String toString() {
        return "MovingAvg";
    }
}
