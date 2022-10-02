package com.binance.trader.classes;

import ch.qos.logback.classic.Logger;
import com.binance.trader.classes.singleton.Logging;

public class IntegerInput extends Input {

    private static final Logger logger = Logging.getInstance();

    public int getUserInput() {
        String inputAsStr = scanner.nextLine();
        int userInput = -1;
        try {
            userInput = Integer.parseInt(inputAsStr);
        } catch (NumberFormatException e) {
            logger.warn("Please enter a valid integer!");
        }
        return userInput;
    }




}
