package org.crypto.bot.classes.builder.indicators;

import org.crypto.bot.classes.indicators.SMAIndicator;
import org.crypto.bot.enums.Period;

public class SMAIndicatorBuilder extends MovingAverageIndicatorBuilder {
    private SMAIndicator indicator;

    @Override
    public void reset() {
        this.indicator = new SMAIndicator();
    }

    public void setPeriod(Period period) {
        this.indicator.setPeriod(period);
    }

    public void setNbOfPeriods(int nbOfPeriods) {
        this.indicator.setNbOfPeriods(nbOfPeriods);
    }

    public SMAIndicator getIndicator() {
        return this.indicator;
    }
}
