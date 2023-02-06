package org.crypto.bot.classes.rules;

import org.crypto.bot.classes.indicators.Indicator;

public class AndRule implements Rule {

    private final Rule firstRule;
    private final Rule secondRule;

    public AndRule(Rule firstRule, Rule secondRule) {
        this.firstRule = firstRule;
        this.secondRule = secondRule;
    }

    @Override
    public void setFirstIndicator(Indicator indicator) {

    }

    @Override
    public void setSecondIndicator(Indicator indicator) {

    }

    @Override
    public boolean isSatisfied(double[] prices) {
        return firstRule.isSatisfied(prices) && secondRule.isSatisfied(prices);
    }
}
