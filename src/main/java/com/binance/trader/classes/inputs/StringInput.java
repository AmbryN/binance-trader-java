package com.binance.trader.classes.inputs;

public class StringInput extends Input {

    public String getUserInput() {
        System.out.println("Selection: ");
        String userInput = this.scanner.nextLine();
        return userInput;
    }




}
