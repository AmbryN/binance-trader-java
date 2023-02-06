package org.crypto.bot.classes.rules;

import org.crypto.bot.classes.indicators.Indicator;

public class UnderIndicatorRule implements Rule {

    private Indicator first;
    private Indicator second;

    public UnderIndicatorRule() {}

    @Override
    public void setFirstIndicator(Indicator indicator) {
        this.first = indicator;
    }

    @Override
    public void setSecondIndicator(Indicator indicator) {
        this.second = indicator;
    }

    @Override
    public boolean isSatisfied(double[] prices) {
        return first.getValue(prices) < second.getValue(prices);
    }
}
