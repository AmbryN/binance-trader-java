package org.crypto.bot.utils;

import org.crypto.bot.Trader;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;

/**
 * Logging singleton for the bot
 */
public class Logging {
    private static Logger instance;

    private Logging() {
        instance = (Logger) LoggerFactory.getLogger(Trader.class);
    }

    /**
     * Used to get the instance of the Logger singleton.
     * @return the logger singleton
     */
    public static ch.qos.logback.classic.Logger getInstance() {
        if (instance == null) new Logging();
        return instance;
    }

}
