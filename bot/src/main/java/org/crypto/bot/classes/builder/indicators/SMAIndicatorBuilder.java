package org.crypto.bot.classes.builder.indicators;

import org.crypto.bot.classes.indicators.SMAIndicator;

public class SMAIndicatorBuilder extends MAIndicatorBuilder {
    private SMAIndicator indicator;

    @Override
    public void reset() {
        this.indicator = new SMAIndicator();
    }

    public void setNbOfPeriods(int nbOfPeriods) {
        this.indicator.setNbOfPeriods(nbOfPeriods);
    }

    @Override
    public SMAIndicator getIndicator() {
        return this.indicator;
    }
}
