package org.crypto.bot.classes.builder.indicators;

import org.crypto.bot.classes.indicators.EMAIndicator;

public class EMAIndicatorBuilder extends MovingAverageIndicatorBuilder {

    @Override
    public void reset() {
        this.indicator = new EMAIndicator();
    }



    public EMAIndicator getIndicator() {
        return (EMAIndicator) this.indicator;
    }
}
