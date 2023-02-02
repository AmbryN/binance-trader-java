package org.crypto.bot.classes.factory;

import org.crypto.bot.classes.builder.*;
import org.crypto.bot.enums.StrategyName;
import org.crypto.bot.interfaces.Strategy;

public class StrategyFactory {
    public static Strategy getStrategyBuilderFor(StrategyName strategy) {
        StrategyDirector director = new StrategyDirector();
        return switch (strategy) {
            case SimpleMovingAverage -> {
                SMAStrategyBuilder builder = new SMAStrategyBuilder();
                director.makeSMAStrategy(builder);
                yield builder.getResult();
            }
            case ExponentialMovingAverage -> {
                EMAStrategyBuilder builder = new EMAStrategyBuilder();
                director.makeEMAStrategy(builder);
                yield builder.getResult();
            }
            case MovingAverageConvergenceDivergence -> {
                MACDStrategyBuilder builder = new MACDStrategyBuilder();
                director.makeMACDStrategy(builder);
                yield builder.getResult();
            }
            case MovingAverageConvergenceDivergenceR1 -> {
                MACDr1StrategyBuilder builder = new MACDr1StrategyBuilder();
                director.makeMACDr1Strategy(builder);
                yield builder.getResult();
            }
            case MovingAverageConvergenceDivergenceR2 -> {
                MACDr2StrategyBuilder builder = new MACDr2StrategyBuilder();
                director.makeMACDStrategy(builder);
                yield builder.getResult();
            }
        };
    };
}
