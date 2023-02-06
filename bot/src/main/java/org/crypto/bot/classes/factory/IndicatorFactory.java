package org.crypto.bot.classes.factory;

import org.crypto.bot.classes.builder.indicators.EMAIndicatorBuilder;
import org.crypto.bot.classes.builder.indicators.IndicatorDirector;
import org.crypto.bot.classes.builder.indicators.MACDIndicatorBuilder;
import org.crypto.bot.classes.builder.indicators.SMAIndicatorBuilder;
import org.crypto.bot.classes.indicators.Indicator;

public class IndicatorFactory {
    public static Indicator getIndicatorBuilder(String indicatorName) {
        IndicatorDirector director = new IndicatorDirector();
        return switch (indicatorName) {
            case "Simple Moving Average" -> {
                SMAIndicatorBuilder builder = new SMAIndicatorBuilder();
                director.makeSMAIndicator(builder);
                yield builder.getIndicator();
            }
            case "Exp. Moving Average" -> {
                EMAIndicatorBuilder builder = new EMAIndicatorBuilder();
                director.makeEMAIndicator(builder);
                yield builder.getIndicator();
            }
            case "Moving Average Convergence Divergence" -> {
                MACDIndicatorBuilder builder = new MACDIndicatorBuilder();
                director.makeMACDIndicator(builder);
                yield builder.getIndicator();
            }
            default -> throw new IllegalArgumentException("Indicator doesn't exist");
        };
    }
}
