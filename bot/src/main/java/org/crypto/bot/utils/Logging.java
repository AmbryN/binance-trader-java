package org.crypto.bot.utils;

import org.crypto.bot.Trader;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;

public class Logging {
    private static Logger instance;

    private Logging() {
        instance = (Logger) LoggerFactory.getLogger(Trader.class);
    }

    public static ch.qos.logback.classic.Logger getInstance() {
        if (instance == null) {
            new Logging();
        }
        return instance;
    }

}
