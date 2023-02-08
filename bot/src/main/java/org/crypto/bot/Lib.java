package org.crypto.bot;

import org.crypto.bot.classes.exchange.BinanceExchange;
import org.crypto.bot.classes.indicators.*;
import org.crypto.bot.classes.rules.*;
import org.crypto.bot.classes.strategies.Strategy;
import org.crypto.bot.enums.Period;
import org.crypto.bot.enums.Symbol;

public class Lib {
    public static void main(String[] args) {
        Period period = Period.OneSecond;

        Indicator MACD = new MACDIndicator(new ClosePriceIndicator(), 12, 26, 9);
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
