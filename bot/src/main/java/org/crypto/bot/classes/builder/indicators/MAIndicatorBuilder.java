package org.crypto.bot.classes.builder.indicators;

import org.crypto.bot.classes.indicators.MAIndicator;

public abstract class MAIndicatorBuilder implements IndicatorBuilder {
    MAIndicator indicator;

    public void setNbOfPeriods(int nbOfPeriods) {
        this.indicator.setNbOfPeriods(nbOfPeriods);
    }

    public abstract MAIndicator getIndicator();
}
