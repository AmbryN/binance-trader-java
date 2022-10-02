package com.binance.trader.classes;

import ch.qos.logback.classic.Logger;
import com.binance.trader.classes.singleton.Logging;

public class IntSelector {
    private static final Logger logger = Logging.getInstance();
    private IntegerInput input;

    public IntSelector() {
        this.input = new IntegerInput();
    }

    public int startSelector() {
        showSelector();
        return input.getUserInput();
    }
    private void showSelector() {
        System.out.println("Please enter the number of periods used for computation: ");
    };
}
