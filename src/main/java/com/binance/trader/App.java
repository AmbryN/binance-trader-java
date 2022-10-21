package com.binance.trader;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.binance.trader.utils.Logging;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.zip.Deflater;

public class App
{
    public static void main( String[] args ) throws InterruptedException {
        Logger logger = Logging.getInstance();
        logger.setLevel(Level.WARN);
        Trader trader = new Trader();
//        FutureTask task = new FutureTask<>(trader, null);
//        ExecutorService executor = Executors.newFixedThreadPool(1);
//
//        executor.execute(task);
        trader.run();
    }
}
