package com.binance.trader;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.binance.trader.entities.MovingAvgStrategy;
import com.binance.trader.enums.Symbol;
import com.binance.trader.intefaces.Strategy;
import com.binance.trader.utils.Logger;

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
            strategy = strategySelection(scanner, symbol);
        }

        Logger.print("=== SUMMARY ===");
        Logger.print("You want to trade :");
        Logger.print("Symbol: " +symbol);
        Logger.print("Strategy: " +strategy);

        if (start(scanner)) {
            Trader trader = new Trader(symbol, strategy);
            trader.trade();  
        } 
        scanner.close();    
    }

    /**
     * The method is used to ask the user to
     * verify the trade parameters and prompt
     * him to accept or decline them 
     * @param scanner Interface to System.in to get user input
     * @return if the user wants to start or not
     */
    private static boolean start(Scanner scanner) {
        Logger.print("Is this OK for you? [y/n]");
        String userInput = getUserInput(scanner).toLowerCase();
        while (!userInput.equals("y") && !userInput.equals("n")) {
            Logger.print("Please choose between 'y' or 'n'!");
            userInput = getUserInput(scanner).toLowerCase();
        }
        if (userInput.equals("y")) {
            return true;
        } else {
            return false;
        } 
    }

    /**
     * The method is used to print the available symbols 
     * the user can trade and to prompt him for a selection
     * @param scanner Interface to System.in to get user input
     * @return the selected Symbol the user wants to trade
     */
    private static Symbol symbolSelection(Scanner scanner) {
        
        System.out.println("What symbol do you want to trade? ");
        Symbol[] symbols = Symbol.values();
        int index = 0;
        for (Symbol symbol : symbols) {
            System.out.println(index + ") " + symbol.name());
            index++;
        }

        String userInput = getUserInput(scanner);
        int userChoice = -1;
        try {
            userChoice = Integer.parseInt(userInput);
        } catch (NumberFormatException e) {
            Logger.print("Please enter a valid integer!");
        }
        
        if (userChoice < 0 || userChoice > Symbol.values().length - 1) {
            System.out.println("Please select one of the proposed choices!");
            return null;
        } else {
            return symbols[userChoice];
        }
    }

    /**
     * The method is used to print the available strategies 
     * the user can use to trade and to prompt him for a selection
     * @param scanner Interface to System.in to get user input
     * @return the select Strategy the user wants to use
     */
    private static Strategy strategySelection(Scanner scanner, Symbol symbol) {
        
        System.out.println("What strategy do you want to use? ");

        List<Strategy> strategies = new ArrayList<Strategy>();
        strategies.add(new MovingAvgStrategy("1m", "25"));

        for (int i = 0; i < strategies.size(); i++) {
            System.out.println(i +") " + strategies.get(i));
        }

        String userInput = getUserInput(scanner);
        int userChoice = -1;
        try {
            userChoice = Integer.parseInt(userInput);
        } catch (NumberFormatException e) {
            Logger.print("Please enter a valid integer!");
        }

        if (userChoice < 0 || userChoice > strategies.size() - 1) {
            System.out.println("Please select one of the proposed choices!");
            return null;
        } else {
            return strategies.get(userChoice);
        }
    }

    /**
     * Method to get user input
     * @param scanner
     * @return User input string
     */
    private static String getUserInput(Scanner scanner) {
        System.out.print("Selection: ");
        
        return scanner.nextLine();
    }
}
