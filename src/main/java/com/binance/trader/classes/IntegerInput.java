package com.binance.trader.classes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntegerInput extends Input {

    private static final Logger logger = LoggerFactory.getLogger(IntegerInput.class);

    public int getUserInput() {
        String inputAsStr = scanner.nextLine();
        int userInput = -1;
        try {
            userInput = Integer.parseInt(inputAsStr);
        } catch (NumberFormatException e) {
            logger.error("Please enter a valid integer!");
        }
        return userInput;
    }




}
