package org.crypto.bot;

import org.crypto.bot.classes.exchange.BinanceExchange;
import org.crypto.bot.classes.indicators.ConstantIndicator;
import org.crypto.bot.classes.indicators.EMAIndicator;
import org.crypto.bot.classes.indicators.MACDIndicator;
import org.crypto.bot.classes.rules.*;
import org.crypto.bot.classes.strategies.Strategy;
import org.crypto.bot.enums.Period;
import org.crypto.bot.enums.Symbol;

public class Lib {
    public static void main(String[] args) {
        Period period = Period.OneSecond;

        Rule entrance = new OverIndicatorRule(
                            new MACDIndicator(12, 26, 9),
                            new EMAIndicator(9)
                        )
                        .and(new OverIndicatorRule(
                                new MACDIndicator(12, 26, 9),
                                new ConstantIndicator(0)
                            )
                        );

        Rule exit = new UnderIndicatorRule(
                            new MACDIndicator(12, 26, 9),
                            new ConstantIndicator(0)
                    );

        Strategy strategy = new Strategy(entrance, exit);
        Trader trader = new Trader(new BinanceExchange(), period, Symbol.BTCUSDT, strategy);
        trader.run();
    }
}
