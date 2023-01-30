package org.crypto.bot.classes.selectors;

import ch.qos.logback.classic.Logger;
import org.crypto.bot.classes.inputs.NumberInput;
import org.crypto.bot.utils.Logging;

public class DoubleSelector {
    private static final Logger logger = Logging.getInstance();
    private NumberInput input;

    public DoubleSelector() {
        this.input = new NumberInput();
    }

    public double startSelector(String message) {
        showSelector(message);
        return input.getUserDouble();
    }
    private void showSelector(String message) {
        System.out.println("Please enter the number for computing " + message + ": ");
    }
}
