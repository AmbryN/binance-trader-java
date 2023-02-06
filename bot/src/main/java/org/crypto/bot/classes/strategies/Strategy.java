package org.crypto.bot.classes.strategies;

import org.crypto.bot.classes.rules.Rule;
import org.crypto.bot.enums.StrategyResult;

/**
 * Trading strategy used by the bot
 */
public class Strategy {
    Rule entranceRule;
    Rule exitRule;

    public Strategy(Rule entranceRule, Rule exitRule) {
        this.entranceRule = entranceRule;
        this.exitRule = exitRule;
    }

    public Rule getEntranceRule() {
        return entranceRule;
    }

    public Rule getExitRule() {
        return exitRule;
    }

    public StrategyResult execute(double tickerPrice, double[] closePrices) {
        if (entranceRule.isSatisfied(tickerPrice, closePrices)) {
            return StrategyResult.BUY;
        } else if (exitRule.isSatisfied(tickerPrice, closePrices)) {
            return StrategyResult.SELL;
        } else {
            return StrategyResult.HOLD;
        }
    };



    public String toString() {
        return "Strategy" +
                "\nEntrance Rule: " + entranceRule +
                "\nExit Rule: " + exitRule;
    }
}
