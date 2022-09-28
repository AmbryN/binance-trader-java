package com.binance.trader;

import com.binance.trader.classes.StartSelector;
import com.binance.trader.classes.StrategyListSelector;
import com.binance.trader.classes.SymbolListSelector;

import com.binance.trader.enums.Symbol;
import com.binance.trader.intefaces.Strategy;

public class App 
{
    public static void main( String[] args )
    {
        int start = 0;
        Symbol symbol = null;
        Strategy strategy = null;

        while(start == 0) {
            symbol = null;
            strategy = null;

            while (symbol == null) {
                symbol = symbolSelection();
            }

            while (strategy == null) {
                strategy = strategySelection();
            }

            System.out.println("=== SUMMARY === \nYou want to trade: \nSymbol: " + symbol + "\nStrategy: " + strategy);
            start = shouldStart();
            while (start == -1) {
                System.out.println("=== SUMMARY === \nYou want to trade: \nSymbol: " + symbol + "\nStrategy: " + strategy);
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
    private static int shouldStart() {
        StartSelector selector = new StartSelector();
        return selector.startSelector();
    }

    /**
     * The method is used to print the available symbols 
     * the user can trade and to prompt him for a selection
     * @return the selected Symbol the user wants to trade
     */
    private static Symbol symbolSelection() {
        SymbolListSelector selector = new SymbolListSelector();
        return selector.startSelector();
    }

    /**
     * The method is used to print the available strategies 
     * the user can use to trade and to prompt him for a selection
     * @return the select Strategy the user wants to use
     */
    private static Strategy strategySelection() {
        StrategyListSelector selector = new StrategyListSelector();
        return selector.startSelector();
    }
}
