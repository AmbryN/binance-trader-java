package org.crypto.bot.classes.builder.indicators;

import org.crypto.bot.classes.indicators.MACDIndicator;

public class MACDIndicatorBuilder implements IndicatorBuilder {
    private MACDIndicator indicator;

    @Override
    public void reset() {
        this.indicator = new MACDIndicator();
    }

    public void setShortNbOfPeriods(int shortNbOfPeriods) {
        this.indicator.setShortNbOfPeriods(shortNbOfPeriods);
    }

    public void setLongNbOfPeriods(int longNbOfPeriods) {
        this.indicator.setLongNbOfPeriods(longNbOfPeriods);
    }

    public MACDIndicator getIndicator() {
        return this.indicator;
    }
}
