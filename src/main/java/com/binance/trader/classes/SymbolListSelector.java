package com.binance.trader.classes;

import com.binance.trader.enums.Symbol;

public class SymbolListSelector extends ListSelector<Symbol> {

    public SymbolListSelector() {
        this.list = Symbol.values();
    }

    @Override
    protected void showSelector() {
        System.out.println("What symbol do you want to trade? ");
        int index = 0;
        for (Symbol symbol : list) {
            System.out.println(index + ") " + symbol.name());
            index++;
        }
    }
}
