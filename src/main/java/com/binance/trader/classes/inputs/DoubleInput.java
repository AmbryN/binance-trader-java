package com.binance.trader.classes.inputs;

import ch.qos.logback.classic.Logger;
import com.binance.trader.classes.singleton.Logging;

public class DoubleInput extends Input {

    private static final Logger logger = Logging.getInstance();

    public double getUserInput() {
        String inputAsStr = scanner.nextLine();
        double userInput = -1;
        try {
            userInput = Double.parseDouble(inputAsStr);
        } catch (NumberFormatException e) {
            logger.warn("Please enter a valid integer!");
        }
        return userInput;
    }




}
