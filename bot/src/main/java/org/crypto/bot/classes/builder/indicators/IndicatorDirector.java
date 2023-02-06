package org.crypto.bot.classes.builder.indicators;

import org.crypto.bot.classes.indicators.EMAIndicator;
import org.crypto.bot.classes.indicators.MACDIndicator;
import org.crypto.bot.classes.indicators.SMAIndicator;
import org.crypto.bot.classes.selectors.IntSelector;
import org.crypto.bot.classes.selectors.PeriodListSelector;

public class IndicatorDirector {
    public SMAIndicator makeSMAIndicator(SMAIndicatorBuilder builder) {
                builder = new SMAIndicatorBuilder();
                builder.reset();
                builder.setPeriod(new PeriodListSelector().startSelector());
                builder.setNbOfPeriods(new IntSelector().startSelector("Moving Average number of periods"));
                return builder.getIndicator();
    }

    public EMAIndicator makeEMAIndicator(EMAIndicatorBuilder builder) {
        builder = new EMAIndicatorBuilder();
        builder.reset();
        builder.setPeriod(new PeriodListSelector().startSelector());
        builder.setNbOfPeriods(new IntSelector().startSelector("Exp. Moving Average number of periods"));
        return builder.getIndicator();
    }

    public MACDIndicator makeMACDIndicator(MACDIndicatorBuilder builder) {
        builder = new MACDIndicatorBuilder();
        builder.reset();
        builder.setPeriod(new PeriodListSelector().startSelector());
        IntSelector selector = new IntSelector();
        builder.setShortNbOfPeriods(selector.startSelector("Short number of periods"));
        builder.setLongNbOfPeriods(selector.startSelector("Long number of periods"));
        builder.setSignalNbOfPeriods(selector.startSelector("Signal number of periods"));
        return builder.getIndicator();
    }
}
