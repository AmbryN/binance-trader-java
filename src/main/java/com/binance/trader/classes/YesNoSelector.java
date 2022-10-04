package com.binance.trader.classes;

import ch.qos.logback.classic.Logger;
import com.binance.trader.classes.inputs.StringInput;
import com.binance.trader.classes.singleton.Logging;

public class YesNoSelector {
    private static final Logger logger = Logging.getInstance();
    private StringInput input;

    public YesNoSelector() {
        this.input = new StringInput();
    }
    public int startSelector() {
        showSelector();
        String userInput = input.getUserInput();
        if (userInput.equalsIgnoreCase("y")) {
            return 1;
        }
        if (userInput.equalsIgnoreCase("n")) {
            return 0;
        }
        logger.warn("Please select one of the proposed choices!");
        return -1;
    }
    private void showSelector() {
        System.out.println("Is this OK for you? [y/n]");
    };
}
