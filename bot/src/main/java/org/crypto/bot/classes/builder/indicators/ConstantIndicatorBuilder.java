package org.crypto.bot.classes.builder.indicators;

import org.crypto.bot.classes.indicators.ConstantIndicator;

public class ConstantIndicatorBuilder implements IndicatorBuilder {
    private ConstantIndicator indicator;

    @Override
    public void reset() {
        this.indicator = new ConstantIndicator();
    }

    public void setValue(double value) {
        this.indicator.setValue(value);
    }

    public ConstantIndicator getIndicator() {
        return this.indicator;
    }
}
