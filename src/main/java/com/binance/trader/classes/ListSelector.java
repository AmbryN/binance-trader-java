package com.binance.trader.classes;

import ch.qos.logback.classic.Logger;
import com.binance.trader.classes.singleton.Logging;

abstract class ListSelector<T> {
    protected T[] list;
    private IntegerInput input;
    private static final Logger logger = Logging.getInstance();

    public ListSelector() {
        this.input = new IntegerInput();
    }
    public T startSelector() {
        showSelector();
        System.out.println("Selection: ");
        int userInput = input.getUserInput();
        if (validateInput(userInput)) return list[userInput];
        return null;
    }
    protected abstract void showSelector();

    private boolean validateInput(int userChoice) {
        if (userChoice < 0 || userChoice > list.length - 1) {
            logger.warn("Please select one of the proposed choices!");
            return false;
        } else {
            return true;
        }
    }
}
