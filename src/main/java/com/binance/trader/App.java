package com.binance.trader;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.binance.trader.exceptions.BinanceTraderException;
import com.binance.trader.utils.Logging;

public class App
{
    public static void main( String[] args )
    {
        Logger logger = Logging.getInstance();
        logger.setLevel(Level.WARN);
        Trader trader = new Trader();
        trader.start();
    }
}
