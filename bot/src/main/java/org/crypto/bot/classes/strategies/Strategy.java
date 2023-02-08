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

    /**
     * Gets the Rule used for entering a trade
     * @return the entrance rule
     */
    public Rule getEntranceRule() {
        return entranceRule;
    }

    /**
     * Get the Rule used for exiting a trade
     * @return the exit rule
     */
    public Rule getExitRule() {
        return exitRule;
    }

    /**
     * Computes the strategy and verifies if either the entrance
     * or exit rule is satisfied, returning the corresponding Strategy result.
     * @param tickerPrice the current price of the crypto pair
     * @param closePrices the last close prices for the crypto pair
     * @return the result of the strategy
     */
    public StrategyResult execute(double tickerPrice, double[] closePrices) {
        if (entranceRule.isSatisfied(tickerPrice, closePrices) && !exitRule.isSatisfied(tickerPrice, closePrices)) {
            return StrategyResult.BUY;
        } else if (exitRule.isSatisfied(tickerPrice, closePrices) && !entranceRule.isSatisfied(tickerPrice, closePrices)) {
            return StrategyResult.SELL;
        } else {
            return StrategyResult.HOLD;
        }
    };

    public String toString() {
        return  "\nEntrance Rule: " + entranceRule +
                "\nExit Rule: " + exitRule;
    }
}
