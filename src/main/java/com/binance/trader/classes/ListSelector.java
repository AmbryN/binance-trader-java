package com.binance.trader.classes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

abstract class ListSelector<T> {
    protected T[] list;
    private IntegerInput input;
    private static final Logger logger = LoggerFactory.getLogger(ListSelector.class);

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
            logger.error("Please select one of the proposed choices!");
            return false;
        } else {
            return true;
        }
    }
}
