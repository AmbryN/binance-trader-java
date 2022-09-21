package com.binance.trader;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.binance.trader.enums.Strategy;
import com.binance.trader.enums.Symbol;

public class App 
{
    public static void main( String[] args )
    {
        Scanner scanner = new Scanner(System.in);

        Symbol symbol = Symbol.None;
        Strategy strategy = Strategy.None;

        while (symbol == Symbol.None) {
            symbol = symbolSelection(scanner);
            if (symbol == Symbol.None) {
                System.out.println("Please select one of the proposed choices!");
            }
        }
        while (strategy == Strategy.None) {
            strategy = strategySelection(scanner);
            if (strategy == Strategy.None) {
                System.out.println("Please select one of the proposed choices!");
            }
        }

        scanner.close();

        // Trader trader = new Trader(symbol, strategy);
        // trader.trade();        
    }

    private static Symbol symbolSelection(Scanner scanner) {
        
        System.out.println("What symbol do you want to trader? ");
        for (Symbol symbol : Symbol.values()) {
            System.out.println(symbol.getPosition() + ") " + symbol);
        }

        int userSymbolChoice = getUserInput(scanner);
        return Symbol.getSymbol(userSymbolChoice);
    }

    private static Strategy strategySelection(Scanner scanner) {
        
        System.out.println("What strategy do you want to use? ");
        for (Strategy strategy : Strategy.values()) {
            System.out.println(strategy.getPosition() + ") " + strategy);
        }

        int userStrategyChoice = getUserInput(scanner);
        return Strategy.getStrategy(userStrategyChoice);
    }

    private static int getUserInput(Scanner scanner) {
        System.out.print("Selection: ");
        int userInput = 0;
        try {
            userInput = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Please enter a valid integer!");
        } finally {
            scanner.nextLine();
        }
        return userInput;
    }
}
