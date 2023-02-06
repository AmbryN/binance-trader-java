package org.crypto.bot.classes.factory;

import org.crypto.bot.classes.indicators.Indicator;
import org.crypto.bot.classes.rules.OverIndicatorRule;
import org.crypto.bot.classes.rules.Rule;
import org.crypto.bot.classes.rules.UnderIndicatorRule;
import org.crypto.bot.classes.selectors.IndicatorSelector;

public class RuleFactory {
    public static Rule createRule(String ruleName) {
        Rule rule = switch (ruleName) {
            case "OverIndicator" ->  new OverIndicatorRule();
            case "UnderIndicator" -> new UnderIndicatorRule();
            default -> throw new IllegalArgumentException("Rule doesn't exist");
        };
        String indicatorFirst = new IndicatorSelector().startSelector();
        Indicator first = IndicatorFactory.getIndicatorBuilder(indicatorFirst);
        String indicatorSecond = new IndicatorSelector().startSelector();
        Indicator second = IndicatorFactory.getIndicatorBuilder(indicatorSecond);
        rule.setFirstIndicator(first);
        rule.setSecondIndicator(second);
        return rule;
    }
}
