package org.crypto.bot.classes.inputs;

public class StringInput extends Input {

    public String getUserInput() {
        System.out.println("Selection: ");
        return this.scanner.nextLine();
    }




}
