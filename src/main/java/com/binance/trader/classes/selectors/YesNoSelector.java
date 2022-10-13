package com.binance.trader.classes.selectors;

import ch.qos.logback.classic.Logger;
import com.binance.trader.classes.inputs.StringInput;
import com.binance.trader.utils.Logging;

public class YesNoSelector {
    private static final Logger logger = Logging.getInstance();
    private StringInput input;

    public YesNoSelector() {
        this.input = new StringInput();
    }
    public boolean startSelector() {
        showSelector();
        String userInput = input.getUserInput();
        while (!userInput.equalsIgnoreCase("y") && !userInput.equalsIgnoreCase("n")) {
            logger.warn("Please select one of the proposed choices!");
            userInput = input.getUserInput();
        }
        return userInput.equalsIgnoreCase("y");
    }
    private void showSelector() {
        System.out.println("Is this OK for you? [y/n]");
    }
}
