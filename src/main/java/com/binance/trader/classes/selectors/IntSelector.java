package com.binance.trader.classes.selectors;

import ch.qos.logback.classic.Logger;
import com.binance.trader.classes.inputs.IntegerInput;
import com.binance.trader.classes.singleton.Logging;

public class IntSelector {
    private static final Logger logger = Logging.getInstance();
    private IntegerInput input;

    public IntSelector() {
        this.input = new IntegerInput();
    }

    public int startSelector(String message) {
        showSelector(message);
        return input.getUserInput();
    }
    private void showSelector(String message) {
        System.out.println("Please enter the number of periods used for computing " + message + ": ");
    };
}
