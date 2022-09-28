package com.binance.trader.classes;

public class StartSelector extends YesNoSelector {

    public StartSelector() {}

    @Override
    void showSelector() {
        System.out.println("Is this OK for you? [y/n]");
    }
}
