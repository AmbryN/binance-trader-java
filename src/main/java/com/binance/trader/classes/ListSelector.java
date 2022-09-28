package com.binance.trader.classes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

abstract class ListSelector<T> {
    protected T[] list;
    private static final Logger logger = LoggerFactory.getLogger(ListSelector.class);

    public T startSelector() {
        showSelector();
        int userInput = getUserInput();
        if (validateInput(userInput)) {
            return list[userInput];
        } else {
            return null;
        }

    }
    abstract void showSelector();
    int getUserInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Selection: ");
        String userInput = scanner.nextLine();

        int userChoice = -1;
        try {
            userChoice = Integer.parseInt(userInput);
        } catch (NumberFormatException e) {
            logger.error("Please enter a valid integer!");
        }
        return userChoice;
    }
    boolean validateInput(int userChoice) {
        if (userChoice < 0 || userChoice > list.length - 1) {
            logger.error("Please select one of the proposed choices!");
            return false;
        } else {
            return true;
        }
    }
}
