package com.binance.trader.classes.inputs;

import ch.qos.logback.classic.Logger;
import com.binance.trader.utils.Logging;

public class NumberInput extends Input {

    private static final Logger logger = Logging.getInstance();

    public int getUserInt() {
        String inputAsStr = scanner.nextLine();
        int userInput = -1;
        try {
            userInput = Integer.parseInt(inputAsStr);
        } catch (NumberFormatException e) {
            logger.warn("Please enter a valid integer!");
        }
        return userInput;
    }

    public double getUserDouble() {
        String inputAsStr = scanner.nextLine();
        double userInput = -1.;
        try {
            userInput = Double.parseDouble(inputAsStr);
        } catch (NumberFormatException e) {
            logger.warn("Please enter a valid double!");
        }
        return userInput;
    }
}
