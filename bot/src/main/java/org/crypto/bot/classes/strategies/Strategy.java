package org.crypto.bot.classes.strategies;

import org.crypto.bot.classes.rules.Rule;
import org.crypto.bot.enums.Period;
import org.crypto.bot.enums.StrategyResult;

/**
 * Trading strategy used by the bot
 */
public class Strategy {
    Rule entranceRule;
    Rule exitRule;
    Period period;

    public Strategy(Period period, Rule entranceRule, Rule exitRule) {
        this.period = period;
        this.entranceRule = entranceRule;
        this.exitRule = exitRule;
    }

    public Period getPeriod() {
        return period;
    }

    public Rule getEntranceRule() {
        return entranceRule;
    }

    public Rule getExitRule() {
        return exitRule;
    }

    public StrategyResult execute(double tickerPrice, double[] closePrices) {
//        if (entranceRule.isSatisfied()) {
//            return StrategyResult.BUY;
//        } else if (exitRule.isSatisfied()) {
//            return StrategyResult.SELL;
//        } else {
            return StrategyResult.HOLD;
//        }
    };

    public String describe() {
        return "Strategy" +
                "\nPeriod: " + period +
                "\nEntrance Rule: " + entranceRule +
                "\nExit Rule: " + exitRule;
    }
}
