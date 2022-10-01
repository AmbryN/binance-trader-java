package com.binance.trader;

import com.binance.trader.classes.StrategyListSelector;
import com.binance.trader.classes.SymbolListSelector;
import com.binance.trader.classes.YesNoSelector;
import com.binance.trader.enums.Symbol;
import com.binance.trader.intefaces.Strategy;

public class Trader {
    int start = 0;
    Strategy strategy;
    Symbol symbol;

    public Trader() {}

    public void start() {
        while(start == 0) {
            symbol = null;
            strategy = null;

            while (symbol == null) {
                symbol = symbolSelection();
            }

            while (strategy == null) {
                strategy = strategySelection();
            }
            strategy.init();

            start = -1;
            while (start == -1) {
                System.out.println("=== SUMMARY === \nYou want to trade: \nSymbol: " + symbol +
                        "\nStrategy: " + strategy + "\nTime period: " + strategy.getPeriod() +
                        "(" + strategy.getPeriod().asString() + ")\nNumber of periods: " + strategy.getNbOfPeriods());
                start = shouldStart();
            }
        }
        strategy.execute(symbol);
    }

    /**
     * The method is used to ask the user to
     * verify the trade parameters and prompt
     * him to accept or decline them
     * @return if the user wants to start or not
     */
    private int shouldStart() {
        YesNoSelector selector = new YesNoSelector();
        return selector.startSelector();
    }

    /**
     * The method is used to print the available symbols
     * the user can trade and to prompt him for a selection
     * @return the selected Symbol the user wants to trade
     */
    private Symbol symbolSelection() {
        SymbolListSelector selector = new SymbolListSelector();
        return selector.startSelector();
    }

    /**
     * The method is used to print the available strategies
     * the user can use to trade and to prompt him for a selection
     * @return the select Strategy the user wants to use
     */
    private Strategy strategySelection() {
        StrategyListSelector selector = new StrategyListSelector();
        return selector.startSelector();
    }
}
