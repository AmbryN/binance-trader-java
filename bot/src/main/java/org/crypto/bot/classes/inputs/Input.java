package org.crypto.bot.classes.inputs;

import java.util.Scanner;

abstract class Input {

    protected Scanner scanner;

    public Input() { this.scanner = new Scanner(System.in); }

}
