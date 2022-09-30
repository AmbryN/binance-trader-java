package com.binance.trader.classes;

public class StartSelector extends YesNoSelector {

    @Override
    protected void showSelector() {
        System.out.println("Is this OK for you? [y/n]");
    }
}
