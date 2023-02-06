package org.crypto.bot.classes.factory;

import org.crypto.bot.classes.builder.indicators.*;
import org.crypto.bot.classes.indicators.Indicator;
import org.crypto.bot.enums.IndicatorEnum;

public class IndicatorFactory {
    public static Indicator getIndicatorBuilder(IndicatorEnum indicatorName) {
        IndicatorDirector director = new IndicatorDirector();
        return switch (indicatorName) {
            case Constant -> {
                ConstantIndicatorBuilder builder = new ConstantIndicatorBuilder();
                director.makeConstantIndicator(builder);
                yield builder.getIndicator();
            }
            case SimpleMovingAverage -> {
                SMAIndicatorBuilder builder = new SMAIndicatorBuilder();
                director.makeMAIndicator(builder);
                yield builder.getIndicator();
            }
            case ExpMovingAverage -> {
                EMAIndicatorBuilder builder = new EMAIndicatorBuilder();
                director.makeMAIndicator(builder);
                yield builder.getIndicator();
            }
            case MACD -> {
                MACDIndicatorBuilder builder = new MACDIndicatorBuilder();
                director.makeMACDIndicator(builder);
                yield builder.getIndicator();
            }
        };
    }
}
