package com.binance.trader.classes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class StringInput extends Input {

    public String getUserInput() {
        System.out.println("Selection: ");
        String userInput = this.scanner.nextLine();
        return userInput;
    }




}
