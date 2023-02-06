package org.crypto.bot.classes.builder.indicators;

import org.crypto.bot.classes.indicators.MovingAverageIndicator;
import org.crypto.bot.enums.Period;

public abstract class MovingAverageIndicatorBuilder implements IndicatorBuilder {
    MovingAverageIndicator indicator;
    public void setPeriod(Period period) {
        this.indicator.setPeriod(period);
    }

    public void setNbOfPeriods(int nbOfPeriods) {
        this.indicator.setNbOfPeriods(nbOfPeriods);
    }
}
