package org.crypto.bot.classes.inputs;

import ch.qos.logback.classic.Logger;
import org.crypto.bot.utils.Logging;

/**
 * Wrapper around the Scanner used to get a number from the user.
 */
public class NumberInput extends Input {

    private static final Logger logger = Logging.getInstance();

    /**
     * Gets an Integer from the user and performs checks on it
     * before returning it.
     * @return the user's integer
     */
    public int getUserInt() {
        String inputAsStr = scanner.nextLine();
        int userInput = -1;
        try {
            userInput = Integer.parseInt(inputAsStr);
        } catch (NumberFormatException e) {
            logger.warn("Please enter a valid integer!");
        }
        return userInput;
    }

    /**
     * Gets a Double from the user and performs checks on it
     * before returning it.
     * @return the user's double
     */
    public double getUserDouble() {
        String inputAsStr = scanner.nextLine();
        double userInput = -1.;
        try {
            userInput = Double.parseDouble(inputAsStr);
        } catch (NumberFormatException e) {
            logger.warn("Please enter a valid double!");
        }
        return userInput;
    }
}
