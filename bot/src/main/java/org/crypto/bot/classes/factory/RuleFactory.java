package org.crypto.bot.classes.factory;

import org.crypto.bot.classes.indicators.ConstantIndicator;
import org.crypto.bot.classes.indicators.Indicator;
import org.crypto.bot.classes.rules.OverIndicatorRule;
import org.crypto.bot.classes.rules.Rule;
import org.crypto.bot.classes.rules.UnderIndicatorRule;
import org.crypto.bot.classes.selectors.IndicatorSelector;
import org.crypto.bot.enums.IndicatorEnum;
import org.crypto.bot.enums.RuleEnum;

public class RuleFactory {
    public static Rule createRule(RuleEnum ruleName) {

        IndicatorEnum indicatorFirst = new IndicatorSelector().startSelector();
        Indicator first = IndicatorFactory.getIndicatorBuilder(indicatorFirst);
        IndicatorEnum indicatorSecond = new IndicatorSelector().startSelector();
        Indicator second = IndicatorFactory.getIndicatorBuilder(indicatorSecond);

        return switch (ruleName) {
            case PriceHigherThan -> null;
            case PriceLowerThan -> null;
            case OverIndicator->  new OverIndicatorRule(first, second);
            case UnderIndicator -> new UnderIndicatorRule(first, second);
        };
    }
}
