package org.crypto.bot;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.crypto.bot.classes.exchange.BinanceExchange;
import org.crypto.bot.classes.indicators.*;
import org.crypto.bot.classes.rules.*;
import org.crypto.bot.classes.strategies.Strategy;
import org.crypto.bot.enums.Period;
import org.crypto.bot.enums.Symbol;
import org.crypto.bot.utils.Logging;

/**
 * Entry point class for running in console mode.
 */
public class App {
    /**
     * Used as a point of entry when running the bot in console mode.
     */
    public static void main(String[] args) {
        Logger logger = Logging.getInstance();
        logger.setLevel(Level.WARN);

        Period period = Period.OneSecond;

        Indicator MACD = new MACDIndicator(new ClosePriceIndicator(), 12, 26);
        Indicator signal = new EMAIndicator(MACD, 9);
        Indicator subtraction = new SubtractIndicator(MACD, signal);

        Rule entrance = new OverIndicatorRule(
                subtraction,
                new ConstantIndicator(0)
        ).and(new OverIndicatorRule(
                MACD,
                new ConstantIndicator(0)
        ));

        Rule exit = new UnderIndicatorRule(
                subtraction,
                new ConstantIndicator(0)
        );

        Strategy strategy = new Strategy(entrance, exit);
        Trader trader = new Trader(new BinanceExchange(), period, Symbol.BTCUSDT, strategy);
        trader.run();
    }
}
