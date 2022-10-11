package com.binance.trader.classes.selectors;

import com.binance.trader.enums.AvailableStrategy;

public class StrategyListSelector extends ListSelector<AvailableStrategy> {

    public StrategyListSelector() {
        this.list = AvailableStrategy.values();
    }

    @Override
    protected void showSelector() {
        System.out.println("What strategy do you want to use? ");
        int index = 0;
        for (AvailableStrategy strategy : list) {
            System.out.println(index + ") " + strategy.name());
            index++;
        }
//        for (int i = 0; i < list.length; i++) {
//            System.out.println(i +") " + list[i]);
//        }
    }
}
