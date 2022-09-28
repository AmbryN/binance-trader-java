package com.binance.trader.classes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

abstract class YesNoSelector {
    private static final Logger logger = LoggerFactory.getLogger(YesNoSelector.class);

    public int startSelector() {
        showSelector();
        String userInput = getUserInput();
        return validateInput(userInput);
    }
    abstract void showSelector();
    String getUserInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Selection: ");
        return scanner.nextLine();
    }
    int validateInput(String userChoice) {
        if (userChoice.equalsIgnoreCase("y")) {
            return 1;
        }
        if (userChoice.equalsIgnoreCase("n")) {
            return 0;
        }
        logger.error("Please select one of the proposed choices!");
        return -1;
    }
}
