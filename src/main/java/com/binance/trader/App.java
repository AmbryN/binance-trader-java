package com.binance.trader;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.binance.trader.entities.MovingAvgStrategy;
import com.binance.trader.enums.Symbol;
import com.binance.trader.intefaces.Strategy;

public class App 
{
    public static void main( String[] args )
    {
        Scanner scanner = new Scanner(System.in);

        Symbol symbol = null;
        Strategy strategy = null;

        while (symbol == null) {
            symbol = symbolSelection(scanner);
        }
        while (strategy == null) {
            strategy = strategySelection(scanner);
        }

        scanner.close();

        Trader trader = new Trader(symbol, strategy);
        trader.trade();        
    }

    /**
     * The method is used to print the available symbols 
     * the user can trade and to prompt him for a selection
     * @param scanner Interface to System.in to get user input
     * @return the selected Symbol the user wants to trade
     */
    private static Symbol symbolSelection(Scanner scanner) {
        
        System.out.println("What symbol do you want to trade? ");
        for (Symbol symbol : Symbol.values()) {
            System.out.println(symbol.getPosition() + ") " + symbol);
        }

        int userChoice = getUserInput(scanner);
        if (userChoice < 0 || userChoice > Symbol.values().length - 1) {
            System.out.println("Please select one of the proposed choices!");
            return null;
        } else {
            return Symbol.getSymbol(userChoice);
        }
    }

    /**
     * The method is used to print the available strategies 
     * the user can use to trade and to prompt him for a selection
     * @param scanner Interface to System.in to get user input
     * @return the select Strategy the user wants to use
     */
    private static Strategy strategySelection(Scanner scanner) {
        
        System.out.println("What strategy do you want to use? ");

        List<Strategy> strategies = new ArrayList<Strategy>();
        strategies.add(new MovingAvgStrategy());

        for (int i = 0; i < strategies.size(); i++) {
            System.out.println(i +") " + strategies.get(i));
        }

        int userChoice = getUserInput(scanner);
        if (userChoice < 0 || userChoice > strategies.size() - 1) {
            System.out.println("Please select one of the proposed choices!");
            return null;
        } else {
            return strategies.get(userChoice);
        }
    }

    /**
     * Method to get and to check the validiy of
     * the user input
     * @param scanner
     * @return int of the selected answer
     */
    private static int getUserInput(Scanner scanner) {
        System.out.print("Selection: ");
        int userInput = -1;
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
