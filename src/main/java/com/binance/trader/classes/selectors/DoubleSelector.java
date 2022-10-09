package com.binance.trader.classes.selectors;

import ch.qos.logback.classic.Logger;
import com.binance.trader.classes.inputs.DoubleInput;
import com.binance.trader.classes.singleton.Logging;

public class DoubleSelector {
    private static final Logger logger = Logging.getInstance();
    private DoubleInput input;

    public DoubleSelector() {
        this.input = new DoubleInput();
    }

    public double startSelector(String message) {
        showSelector(message);
        return input.getUserInput();
    }
    private void showSelector(String message) {
        System.out.println("Please enter the number of periods used for computing " + message + ": ");
    };
}
