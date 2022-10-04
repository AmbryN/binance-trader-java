package com.binance.trader.classes.inputs;

import java.util.Scanner;

public abstract class Input {

    protected Scanner scanner;

    public Input() { this.scanner = new Scanner(System.in); }

}
