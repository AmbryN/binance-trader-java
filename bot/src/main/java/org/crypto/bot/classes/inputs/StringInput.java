package org.crypto.bot.classes.inputs;

/**
 * Wrapper around the Scanner for getting a String input from the user.
 */
public class StringInput extends Input {

    /**
     * Gets a String from the User
     * @return the user's String
     */
    public String getUserInput() {
        System.out.println("Selection: ");
        return this.scanner.nextLine();
    }
}
