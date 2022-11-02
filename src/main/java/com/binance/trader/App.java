package com.binance.trader;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.binance.trader.utils.Logging;

import java.util.concurrent.ExecutionException;

public class App
{
    public static void main( String[] args ) throws ExecutionException, InterruptedException {
        Logger logger = Logging.getInstance();
        logger.setLevel(Level.WARN);
        Trader trader = new Trader();
        trader.run();
    }
}
