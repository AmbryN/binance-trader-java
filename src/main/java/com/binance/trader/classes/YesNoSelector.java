package com.binance.trader.classes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

abstract class YesNoSelector {
    private static final Logger logger = LoggerFactory.getLogger(YesNoSelector.class);
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
        logger.error("Please select one of the proposed choices!");
        return -1;
    }
    protected abstract void showSelector();
}
