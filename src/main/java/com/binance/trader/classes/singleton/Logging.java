package com.binance.trader.classes.singleton;

import com.binance.trader.App;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;

public class Logging {
    private static Logger instance;

    private Logging() {
        this.instance = (Logger) LoggerFactory.getLogger(App.class);
    }

    public static ch.qos.logback.classic.Logger getInstance() {
        if (instance == null) {
            new Logging();
        }
        return instance;
    }

}
