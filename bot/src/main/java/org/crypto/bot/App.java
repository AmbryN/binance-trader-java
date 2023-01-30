package org.crypto.bot;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.crypto.bot.utils.Logging;

public class App
{
    public static void main( String[] args ) throws InterruptedException {
        Logger logger = Logging.getInstance();
        logger.setLevel(Level.WARN);
        Trader trader = new Trader();
        trader.run();
    }
}
