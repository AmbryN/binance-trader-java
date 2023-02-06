package org.crypto.bot;

import org.crypto.bot.classes.exchange.BinanceExchange;
import org.crypto.bot.classes.indicators.EMAIndicator;
import org.crypto.bot.classes.indicators.MACDIndicator;
import org.crypto.bot.classes.indicators.SMAIndicator;
import org.crypto.bot.classes.rules.*;
import org.crypto.bot.classes.strategies.Strategy;
import org.crypto.bot.enums.Period;
import org.crypto.bot.enums.Symbol;

public class Lib {
    public static void main(String[] args) {
        Period period = Period.FiveMinutes;
        Rule entrance = new AndRule(new OverIndicatorRule(new MACDIndicator(12, 26, 9), new EMAIndicator(15)), new PriceHigherThan(22000));
        Rule exit = new OrRule(new PriceLowerThan(19000), new UnderIndicatorRule(new SMAIndicator(15), new EMAIndicator(15)));
        Strategy strategy = new Strategy(entrance, exit);
        Trader trader = new Trader(new BinanceExchange(), period, Symbol.BTCUSDT, strategy);
        trader.run();
    }
}
