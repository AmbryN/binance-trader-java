package org.crypto.bot.classes.selectors;

import ch.qos.logback.classic.Logger;
import org.crypto.bot.classes.inputs.NumberInput;
import org.crypto.bot.utils.Logging;

public class IntSelector {
    private static final Logger logger = Logging.getInstance();
    private NumberInput input;

    public IntSelector() {
        this.input = new NumberInput();
    }

    public int startSelector(String message) {
        showSelector(message);
        return input.getUserInt();
    }
    private void showSelector(String message) {
        System.out.println("Please enter the number for computing " + message + ": ");
    }
}
