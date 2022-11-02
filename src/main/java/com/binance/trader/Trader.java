package com.binance.trader;

import com.binance.trader.classes.data.TraderConfig;
import com.binance.trader.classes.handlers.Try;
import com.binance.trader.classes.selectors.*;
import com.binance.trader.runners.StrategyRunner;
import com.binance.trader.runners.TransactionRunner;

import java.util.concurrent.*;

public class Trader {
    private final ScheduledExecutorService es;
    private TraderConfig traderConfig;

    public Trader() {
        this.es = Executors.newScheduledThreadPool(2);
    }

    public void run()  {
        boolean start = false;
        while(!start) {
            traderConfig = new TraderConfig();
            traderConfig.describe();
            start = new YesNoSelector().startSelector();
        }

        ScheduledFuture<?> strategyFuture = es.scheduleAtFixedRate(new StrategyRunner(traderConfig), 0, 5, TimeUnit.SECONDS);
        ScheduledFuture<?> transactionFuture = es.scheduleAtFixedRate(new TransactionRunner(traderConfig), 1, 1, TimeUnit.MINUTES);

        // If method get() returns a value, it means an exception was thrown in the child thread
        Try.toGet(strategyFuture::get);
        Try.toGet(transactionFuture::get);
    }
}
