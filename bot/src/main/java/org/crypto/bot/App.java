package org.crypto.bot;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.crypto.bot.classes.exchange.BinanceExchange;
import org.crypto.bot.classes.exchange.Exchange;
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

        Period period = Period.OneMinute;

        Indicator MACD = new MACDIndicator(new PriceIndicator(), 12, 26);
        Indicator signal = new EMAIndicator(MACD, 9);
        Indicator subtraction = new SubtractIndicator(MACD, signal);

        Rule entrance = new OverIndicatorRule(
                subtraction,
                new ConstantIndicator(0)
        );

        Rule exit = new UnderIndicatorRule(
                subtraction,
                new ConstantIndicator(0)
        );

        Strategy strategy = new Strategy(entrance, exit);

        Exchange binance = new BinanceExchange();

        Trader trader = new Trader(binance, period, Symbol.BTCUSDT, strategy);
        trader.run();
    }
}
