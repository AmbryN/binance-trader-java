package org.crypto.bot.classes.builder.indicators;

import org.crypto.bot.classes.indicators.MACDIndicator;
import org.crypto.bot.enums.Period;

public class MACDIndicatorBuilder implements IndicatorBuilder {
    private MACDIndicator indicator;

    @Override
    public void reset() {
        this.indicator = new MACDIndicator();
    }

    public void setPeriod(Period period) {
        this.indicator.setPeriod(period);
    }

    public void setShortNbOfPeriods(int shortNbOfPeriods) {
        this.indicator.setShortNbOfPeriods(shortNbOfPeriods);
    }

    public void setLongNbOfPeriods(int longNbOfPeriods) {
        this.indicator.setLongNbOfPeriods(longNbOfPeriods);
    }

    public void setSignalNbOfPeriods(int signalNbOfPeriods) {
        this.indicator.setSignalNbOfPeriods(signalNbOfPeriods);
    }

    public MACDIndicator getIndicator() {
        return this.indicator;
    }
}
