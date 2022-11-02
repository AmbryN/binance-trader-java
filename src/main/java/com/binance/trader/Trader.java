package com.binance.trader;

import com.binance.trader.classes.data.TraderConfig;
import com.binance.trader.classes.selectors.*;
import com.binance.trader.runners.StrategyRunner;
import com.binance.trader.runners.TransactionRunner;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.*;

public class Trader {
    private final ScheduledExecutorService es;
    private TraderConfig traderConfig;

    public Trader() {
        final ThreadGroup threadGroup = new ThreadGroup("workers");
        this.es = Executors.newScheduledThreadPool(2, new ThreadFactory() {
            @Override
            public Thread newThread(@NotNull Runnable r) {
                Thread t = new Thread(threadGroup, r);
                t.setUncaughtExceptionHandler(new UncaughtThreadExceptionHandler("Handler 1"));
                return t;
            }
        });
    }

    public void run() {
        boolean start = false;
        while(!start) {
            traderConfig = new TraderConfig();
            traderConfig.describe();
            start = new YesNoSelector().startSelector();
        }
        ScheduledFuture<?> strategyFuture = es.scheduleAtFixedRate(new StrategyRunner(traderConfig), 1, 1, TimeUnit.MINUTES);
        ScheduledFuture<?> transactionFuture = es.scheduleAtFixedRate(new TransactionRunner(traderConfig), 0, 5, TimeUnit.SECONDS);

        // If method get() returns a value, it means an exception was thrown in the child thread
        try {
            strategyFuture.get();
            transactionFuture.get();
        } catch (ExecutionException | InterruptedException e) {
            System.exit(1);
        }
    }
}
