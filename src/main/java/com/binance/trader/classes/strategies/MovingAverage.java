package com.binance.trader.classes.strategies;

import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.trader.classes.data.AccountInfo;
import com.binance.trader.classes.selectors.IntSelector;
import com.binance.trader.classes.selectors.PeriodListSelector;
import com.binance.trader.enums.Period;
import com.binance.trader.enums.Symbol;
import com.binance.trader.intefaces.Strategy;
import com.binance.trader.services.AccountInfoService;
import com.binance.trader.services.OrderService;
import com.binance.trader.services.TickerService;

public abstract class MovingAverage implements Strategy {
    protected SpotClientImpl client;
    protected Period period;
    protected int nbOfPeriods;

    public MovingAverage() {
        this.period = null;
        this.nbOfPeriods = -1;
    }

    @Override
    public void init(SpotClientImpl client) {
        this.client = client;
        while (this.period == null) {
            PeriodListSelector selector = new PeriodListSelector();
            this.period = selector.startSelector();
        }
        while (this.nbOfPeriods < 0) {
            IntSelector selector = new IntSelector();
            this.nbOfPeriods = selector.startSelector("Moving Average");
        }
    }

    @Override
    public void execute(Symbol symbol) {

        AccountInfoService accountInfoService = new AccountInfoService(client);
        AccountInfo accountInfo = accountInfoService.getAccountInfo();
        double baseBalance = accountInfo.getBalance(symbol.getBase()).getFreeBalance();
        double quoteBalance = accountInfo.getBalance(symbol.getQuote()).getFreeBalance();
        boolean hasSendAnOrder = false;

        while(true) {
            if (hasSendAnOrder) {
                accountInfo = accountInfoService.getAccountInfo();
                baseBalance = accountInfo.getBalance(symbol.getBase()).getFreeBalance();
                quoteBalance = accountInfo.getBalance(symbol.getQuote()).getFreeBalance();
                hasSendAnOrder = false;
            }
            TickerService tickerService = new TickerService(client);
            double tickerPrice = tickerService.getTicker(symbol).getPrice();
            double movingAvg = this.calculateMovingAvg(symbol);

            System.out.println("Base balance: " + baseBalance + " / Quote balance: " + quoteBalance + " / Ticker " + tickerPrice +
                    " / ExpMAvg " + movingAvg);

            OrderService orderService = new OrderService(this.client);
            if (tickerPrice > movingAvg && quoteBalance > symbol.MIN_QUOTE_TRANSACTION) {
                orderService.buy(symbol, tickerPrice, quoteBalance);
                hasSendAnOrder = true;
            } else if (tickerPrice < movingAvg && baseBalance > symbol.MIN_BASE_TRANSACTION) {
                orderService.sell(symbol, tickerPrice, baseBalance);
                hasSendAnOrder = true;
            }
        }
    }

    protected abstract double calculateMovingAvg(Symbol symbol);

    public abstract String toString();

    @Override
    public String describe() {
        return "Time Period: " + this.period +
                "\n Number of Periods: " + this.nbOfPeriods;
    }
}
