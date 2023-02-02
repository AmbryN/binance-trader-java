package org.crypto.bot.classes.builder;

import org.crypto.bot.classes.selectors.DoubleSelector;
import org.crypto.bot.classes.selectors.IntSelector;
import org.crypto.bot.classes.selectors.PeriodListSelector;

public class StrategyDirector {
    public StrategyDirector() {}

    public void makeSMAStrategy(MovingAvgBuilder builder) {
        builder.reset();
        builder.setPeriod(new PeriodListSelector().startSelector());
        builder.setNbOfPeriods(new IntSelector().startSelector("Moving Average"));
    }

    public void makeEMAStrategy(MovingAvgBuilder builder) {
        builder.reset();
        builder.setPeriod(new PeriodListSelector().startSelector());
        builder.setNbOfPeriods(new IntSelector().startSelector("Exp Moving Average"));
    }

    public void makeMACDStrategy(MACDBuilder builder) {
        builder.reset();
        builder.setPeriod(new PeriodListSelector().startSelector());
        IntSelector selector = new IntSelector();
        builder.setShortNbOfPeriods(selector.startSelector("Short EMA"));
        builder.setLongNbOfPeriods(selector.startSelector("Long EMA"));
        builder.setSignalNbOfPeriods(selector.startSelector("Signal EMA"));
    }

    public void makeMACDr1Strategy(MACDr1StrategyBuilder builder) {
        builder.reset();
        builder.setPeriod(new PeriodListSelector().startSelector());
        IntSelector selector = new IntSelector();
        builder.setShortNbOfPeriods(selector.startSelector("Short EMA"));
        builder.setLongNbOfPeriods(selector.startSelector("Long EMA"));
        builder.setSignalNbOfPeriods(selector.startSelector("Signal EMA"));
        builder.setMinSpread(new DoubleSelector().startSelector("Min Spread before Buy occurs (as double: e.g. 2.5 for 0.025): "));
    }
}
