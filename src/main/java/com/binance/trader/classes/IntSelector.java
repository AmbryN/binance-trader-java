package com.binance.trader.classes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntSelector {
    private static final Logger logger = LoggerFactory.getLogger(IntSelector.class);
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
