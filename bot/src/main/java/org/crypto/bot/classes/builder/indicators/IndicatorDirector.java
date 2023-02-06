package org.crypto.bot.classes.builder.indicators;

import org.crypto.bot.classes.indicators.ConstantIndicator;
import org.crypto.bot.classes.indicators.MACDIndicator;
import org.crypto.bot.classes.indicators.MAIndicator;
import org.crypto.bot.classes.selectors.DoubleSelector;
import org.crypto.bot.classes.selectors.IntSelector;

public class IndicatorDirector {
    public ConstantIndicator makeConstantIndicator(ConstantIndicatorBuilder builder) {
        builder.reset();
        builder.setValue(new DoubleSelector().startSelector("Value of constant indicator"));
        return builder.getIndicator();
    }
    public MAIndicator makeMAIndicator(MAIndicatorBuilder builder) {
                builder.reset();
                builder.setNbOfPeriods(new IntSelector().startSelector("Moving Average number of periods"));
                return builder.getIndicator();
    }

    public MACDIndicator makeMACDIndicator(MACDIndicatorBuilder builder) {
        builder.reset();
        IntSelector selector = new IntSelector();
        builder.setShortNbOfPeriods(selector.startSelector("Short number of periods"));
        builder.setLongNbOfPeriods(selector.startSelector("Long number of periods"));
        builder.setSignalNbOfPeriods(selector.startSelector("Signal number of periods"));
        return builder.getIndicator();
    }
}
