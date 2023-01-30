package org.crypto.bot.classes.selectors;

import ch.qos.logback.classic.Logger;
import org.crypto.bot.classes.inputs.NumberInput;
import org.crypto.bot.utils.Logging;

abstract class ListSelector<T> {
    protected T[] list;
    private NumberInput input;
    private static final Logger logger = Logging.getInstance();

    public ListSelector() {
        this.input = new NumberInput();
    }
    public T startSelector() {
        showSelector();
        System.out.println("Selection: ");
        int userInput = input.getUserInt();
        while(!validateInput(userInput)) {
            userInput = input.getUserInt();
        }
        return list[userInput];
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
